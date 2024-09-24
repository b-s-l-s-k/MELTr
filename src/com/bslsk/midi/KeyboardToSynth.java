package com.bslsk.midi;


import com.bslsk.gen.LineContext;
import com.bslsk.info.Assets;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;
import java.awt.*;

/**
 * Create a connection between a musical keyboard (transmitter) and an internal
 * synthesizer.  You should first run {@link MidiDeviceDisplay} to discover the
 * device names for each of these.
 *
 * @author Knute Snortum
 * @version 2017/06/17
 */
public class KeyboardToSynth {

    /**
     * Name values can have the class name, (see {@link MidiSystem}), the device
     * name or both. Use a pound sign (#) to separate the class and device name.
     * Get device names from the {@link MidiDeviceDisplay} program, or leave
     * empty for default.<p>
     *
     * {@code javax.sound.midi.Transmitter#USB Uno MIDI Interface}<br>
     * {@code javax.sound.midi.Synthesizer#Microsoft MIDI Mapper}<br>
     */
    private static final String TRANS_DEV_NAME = "javax.sound.midi.Transmitter#USB Uno MIDI Interface";
    private static final String SYNTH_DEV_NAME = "javax.sound.midi.Synthesizer#Microsoft MIDI Mapper";

    /** See {@link MidiSystem} for other classes */
    private static final String TRANS_PROP_KEY = "javax.sound.midi.Transmitter";
    private static final String SYNTH_PROP_KEY = "javax.sound.midi.Synthesizer";

    public static void main(String[] args) {
        new KeyboardToSynth().run();
    }

    public void run() {

        // Get a transmitter and synthesizer from their device names
        // using system properties or defaults
        Transmitter trans = getTransmitter();
        Synthesizer synth = getSynthesizer();

        if (trans == null || synth == null) {
            return;
        }

        // The synthesizer is your MIDI device, which needs to be opened
        if (! synth.isOpen()) {
            try {
                synth.open();
            } catch (MidiUnavailableException e) {
                System.err.println("Error opening synthesizer");
                e.printStackTrace();
                return;
            }
        }

        // You get your receiver from the synthesizer, then set it in
        // your transmitter.  Optionally, you can create an implementation
        // of Receiver to display the messages before they're sent.
        try {
            Receiver receiver = synth.getReceiver();
            DisplayReceiver displayReceiver = new DisplayReceiver(receiver); // optional
            trans.setReceiver(displayReceiver); // or just "receiver"

            // You should be able to play on your musical keyboard (transmitter)
            // and hear sounds through your PC synthesizer (receiver)
            System.out.println("Play on your musical keyboard...");
        } catch (MidiUnavailableException e) {
            System.err.println("Error getting receiver from synthesizer");
            e.printStackTrace();
        }
    }

    /**
     * @return a specific synthesizer object by setting the system property, otherwise the default
     */
    private Synthesizer getSynthesizer() {
        if (! SYNTH_DEV_NAME.isEmpty() || ! "default".equalsIgnoreCase(SYNTH_DEV_NAME)) {
            System.setProperty(SYNTH_PROP_KEY, SYNTH_DEV_NAME);
        }

        try {
            return MidiSystem.getSynthesizer();
        } catch (MidiUnavailableException e) {
            System.err.println("Error getting synthesizer");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return a specific transmitter object by setting the system property, otherwise the default
     */
    private Transmitter getTransmitter() {
        if (! TRANS_DEV_NAME.isEmpty() && ! "default".equalsIgnoreCase(TRANS_DEV_NAME)) {
            System.setProperty(TRANS_PROP_KEY, TRANS_DEV_NAME);
        }

        try {
            return MidiSystem.getTransmitter();
        } catch (MidiUnavailableException e) {
            System.err.println("Error getting transmitter");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Implementation of {@link Receiver} that will display the MIDI message
     * before sending it.
     *
     * @author Knute Snortum
     * @version 2017/06/16
     */
    private class DisplayReceiver implements Receiver {
        private Receiver receiver;
        boolean isSystemExclusiveData = false;

        public DisplayReceiver(Receiver receiver) {
            this.receiver = receiver;
        }

        @Override
        public void send(MidiMessage message, long timeStamp) {
            displayMessage(message, timeStamp);
            receiver.send(message, timeStamp);
        }

        @Override
        public void close() {
            receiver.close();
        }

        // Display MIDI message
        private void displayMessage(MidiMessage message, long timeStamp) {

            // Check: Are we printing system exclusive data?
            if (isSystemExclusiveData) {
                displayRawData(message);
                return;
            }

            int status = message.getStatus();

            // These statuses clutter the display
            if ( status == 0xf8 ) { return; } // ignore timing messages
            if ( status == 0xfe ) { return; } // ignore status active

            System.out.printf("%d - Status: 0x%s",
                    timeStamp, Integer.toHexString(status));

            // Strip channel number out of status
            int leftNibble = status & 0xf0;

            // These statuses have MIDI channel numbers and data (except
            // 0xf0 thru 0xff)
            switch (leftNibble) {
                case 0x80: displayNoteOff(message); break;
                case 0x90: displayNoteOn(message); break;
                case 0xa0: displayKeyPressure(message); break;
                case 0xb0: displayControllerChange(message); break;
                case 0xc0: displayProgramChange(message); break;
                case 0xd0: displayChannelPressure(message); break;
                case 0xe0: displayPitchBend(message); break;
                case 0xf0: displaySystemMessage(message); break;
                default:
                    System.out.println(" Unknown status");
                    displayRawData(message);
            }
        }

        // Displays raw data as integers, if any
        private void displayRawData(MidiMessage message) {
            byte[] bytes = message.getMessage();

            if (message.getLength() > 1) {
                System.out.print("\tRaw data: ");

                for (int i = 1; i < bytes.length; i++) {
                    System.out.print(byteToInt(bytes[i]) + " ");
                }

                System.out.println();
            }
        }

        // Display status and data of a NoteOn message.  Data may come
        // in pairs after the status byte.
        //
        // Note that a NoteOn with a velocity of 0 is synonymous with
        // a NoteOff message.
        private void displayNoteOn(MidiMessage message) {
            if (message.getLength() < 3 || message.getLength() % 2 == 0) {
                System.out.println(" Bad MIDI message");
                return;
            }

            byte[] bytes = message.getMessage();

            // Zero velocity
            if ( bytes[2] == 0 ) {
                System.out.print(" = Note off");
            } else {
                System.out.print(" = Note on");
            }

            System.out.print(", Channel " + midiChannelToInt(message));

            if ( bytes[2] == 0 ) {
                System.out.println(", Note " + byteToInt(bytes[1]));
                return;
            }

            System.out.print("\n\t");

            for (int i = 1; i < message.getLength(); i += 2) {
                if ( i > 1 ) {
                    System.out.print("; ");
                }
                System.out.printf( "Number %d, Velocity %d",
                        byteToInt(bytes[i]) , byteToInt(bytes[i + 1]) );
            }

            System.out.println();
        }

        // Display status and data of a NoteOff message.
        private void displayNoteOff(MidiMessage message) {
            if (message.getLength() < 3 || message.getLength() % 2 == 0) {
                System.out.println(" Bad MIDI message");
            } else {
                byte[] bytes = message.getMessage();
                System.out.printf(" = Note off, Channel %d, Note %d%n",
                        midiChannelToInt(message), byteToInt(bytes[1]));
                System.out.println();
            }
        }

        // Display status and data of a ControllerChange message.  Data may come
        // in pairs after the status byte.
        private void displayControllerChange(MidiMessage message) {
            if (message.getLength() < 3 || message.getLength() % 2 == 0) {
                System.out.println(" Bad MIDI message");
                return;
            }
            /*
            System.out.print(" = Controller Change, Channel "
                    + midiChannelToInt(message) + "\n\t");
            message.
            */

            byte[] bytes = message.getMessage();
            for (int i = 1; i < message.getLength(); i += 2) {
                if ( i > 1 ) {
                    System.out.print("; ");
                }
                System.out.printf( "Controller %d, Value %d\n", byteToInt(bytes[i]), byteToInt(bytes[i + 1]) );
                Assets.sendMidi(byteToInt(bytes[i]), byteToInt(bytes[i + 1]));


            }


            System.out.println();
        }

        private void displayControllerChangeBACKUP(MidiMessage message) {
            if (message.getLength() < 3 || message.getLength() % 2 == 0) {
                System.out.println(" Bad MIDI message");
                return;
            }
            /*
            System.out.print(" = Controller Change, Channel "
                    + midiChannelToInt(message) + "\n\t");
            message.
            */

            byte[] bytes = message.getMessage();
            for (int i = 1; i < message.getLength(); i += 2) {
                if ( i > 1 ) {
                    System.out.print("; ");
                }
                System.out.printf( "Controller %d, Value %d\n", byteToInt(bytes[i]), byteToInt(bytes[i + 1]) );
                if(byteToInt(bytes[i]) == 1)
                {
                    Assets.popColor();
                }
                else if(byteToInt(bytes[i]) == 2)
                {
                    Assets.pushColor();
                }
                else if(byteToInt(bytes[i]) == 3)
                {
                    Assets.adjustMidi(0, byteToInt(bytes[i + 1]));
                    //System.out.println("The");
                }
                else if(byteToInt(bytes[i]) == 4)
                {
                    Assets.adjustMidi(1, byteToInt(bytes[i + 1]));
                    //System.out.println("The2");
                }
                else if(byteToInt(bytes[i]) == 5)
                {
                    Assets.adjustMidi(2, byteToInt(bytes[i + 1]));
                    //System.out.println("The3");
                }
                else if(byteToInt(bytes[i]) == 6)
                {
                    Assets.adjustMidi(3, byteToInt(bytes[i + 1]));
                    //System.out.println("The4");
                }

                //KNOBS ABOVE SLIDERS
                if(byteToInt(bytes[i]) >= 14 && byteToInt(bytes[i]) <= 22) // KNOBS 1-8
                {
                    Assets.adjustMidiContext(byteToInt(bytes[i]), byteToInt(bytes[i + 1]));
                    //System.out.println("The");
                }
                else if(byteToInt(bytes[i]) >= 23 && byteToInt(bytes[i]) <= 31)// Buttons below sliders
                {
                    Assets.adjustContextEnabled(byteToInt(bytes[i]), byteToInt(bytes[i + 1]));
                    //System.out.println("The");
                }
                else if(byteToInt(bytes[i]) == 47)
                {
                    ((LineContext)Assets.context[2]).popColor();
                    //System.out.println("The");
                }
                else if(byteToInt(bytes[i]) == 48)
                {
                    ((LineContext)Assets.context[2]).pushColor();
                    //System.out.println("The");
                }
                else if(byteToInt(bytes[i]) == 49) // refresh button
                {
                    Assets.current = Color.BLACK;
                    //System.out.println("The");
                }
                else if(byteToInt(bytes[i]) == 60) // horizontal slider
                {
                    Assets.adjustSpeed(byteToInt(bytes[i + 1]));
                    //System.out.println("The");
                }


            }


            System.out.println();
        }
        // Display status and data of a KeyPressure message.  Data may come
        // in pairs after the status byte.
        private void displayKeyPressure(MidiMessage message) {
            if (message.getLength() < 3 || message.getLength() % 2 == 0) {
                System.out.println(" Bad MIDI message");
                return;
            }

            System.out.print(" = Key Pressure, Channel "
                    + midiChannelToInt(message) + "\n\t");

            byte[] bytes = message.getMessage();
            for (int i = 1; i < message.getLength(); i += 2) {
                if ( i > 1 ) {
                    System.out.print("; ");
                }
                System.out.printf( "Note Number %d, Pressure %d",
                        byteToInt(bytes[i]), byteToInt(bytes[i + 1]) );
            }

            System.out.println();
        }

        // Display status and data of a PitchBend message.  Data may come
        // in pairs after the status byte.
        private void displayPitchBend(MidiMessage message) {
            if (message.getLength() < 3 || message.getLength() % 2 == 0) {
                System.out.println(" Bad MIDI message");
                return;
            }

            System.out.print(" = Pitch Bend, Channel "
                    + midiChannelToInt(message) + "\n\t");

            byte[] bytes = message.getMessage();
            for (int i = 1; i < message.getLength(); i += 2) {
                if ( i > 1 ) {
                    System.out.print("; ");
                }
                System.out.printf( "Value %d",
                        bytesToInt(bytes[i], bytes[i + 1]) );
            }

            System.out.println();
        }

        // Display status and data of a ProgramChange message
        private void displayProgramChange(MidiMessage message) {
            if (message.getLength() < 2) {
                System.out.println(" Bad MIDI message");
                return;
            }

            System.out.print(" = Program Change, Channel "
                    + midiChannelToInt(message) + "\n\t");

            byte[] bytes = message.getMessage();
            for (int i = 1; i < message.getLength(); i++) {
                if ( i > 1 ) {
                    System.out.print(", ");
                }
                System.out.println("Program Number " + byteToInt(bytes[i]));
            }
        }

        // Display status and data of a ChannelPressure message
        private void displayChannelPressure(MidiMessage message) {
            if (message.getLength() < 2) {
                System.out.println(" Bad MIDI message");
                return;
            }

            System.out.print(" = Channel Pressure, Channel "
                    + midiChannelToInt(message) + "\n\t");

            byte[] bytes = message.getMessage();
            for (int i = 1; i < message.getLength(); i++) {
                if ( i > 1 ) {
                    System.out.print(", ");
                }
                System.out.println("Pressure " + byteToInt(bytes[i]));
            }
        }

        // Display system messages.  Some may have data.
        //
        // "Begin System Exclusive" stops data interpretation, "End of
        // System Exclusive" starts it again
        private void displaySystemMessage(MidiMessage message) {
            byte[] bytes = message.getMessage();

            switch (message.getStatus()) {
                case 0xf0:
                    System.out.println(" = Begin System Exclusive");
                    isSystemExclusiveData = true;
                    break;
                case 0xf1:
                    if (bytes.length < 2) {
                        System.out.println(" Bad Data");
                    } else {
                        System.out.println(" = MIDI Time Code 1/4 Frame, Time Code "
                                + byteToInt(bytes[1]));
                    }
                    break;
                case 0xf2:
                    if (bytes.length < 3) {
                        System.out.println(" Bad Data");
                    } else {
                        System.out.println(" = Song Position, Pointer "
                                + bytesToInt(bytes[1], bytes[2]));
                    }
                case 0xf3:
                    if (bytes.length < 2) {
                        System.out.println(" Bad Data");
                    } else {
                        System.out.println(" = Song Select, Song "
                                + byteToInt(bytes[1]));
                    }
                    break;
                case 0xf6:
                    System.out.println(" = Tune Request");
                    break;
                case 0xf7:
                    System.out.println(" = End of System Exclusive");
                    isSystemExclusiveData = false;
                    break;
                case 0xf8:
                    System.out.println(" = Timing Clock"); // ignored
                    break;
                case 0xfa:
                    System.out.println(" = Start");
                    break;
                case 0xfb:
                    System.out.println(" = Continue");
                    break;
                case 0xfc:
                    System.out.println(" = Stop");
                    break;
                case 0xfe:
                    System.out.println(" = Active Sensing"); // ignored
                    break;
                case 0xff:
                    System.out.println(" = System Reset");
                    break;
                default:
                    System.out.println(" Unknow System Message");
                    displayRawData(message);
            }
        }

        private int byteToInt(byte b) {
            return b & 0xff;
        }

        // Two 7-bit bytes
        private int bytesToInt(byte msb, byte lsb) {
            return byteToInt(msb) * 128 + byteToInt(lsb);
        }

        private int midiChannelToInt(MidiMessage message) {
            return (message.getStatus() & 0x0f) + 1;
        }
    }
}