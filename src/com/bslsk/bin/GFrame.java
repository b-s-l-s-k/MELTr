package com.bslsk.bin;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

import com.bslsk.gen.Effect;
import com.bslsk.gen.FilterEffect;
import com.bslsk.gen.GContext;
import com.bslsk.info.Assets;
import com.bslsk.info.KeyMapper;
import com.bslsk.info.Preferences;
import com.bslsk.info.Recorder;
import com.bslsk.midi.KeyboardToSynth;
import com.bslsk.midi.MidiHandler;
import com.bslsk.spout.MApplet;
import com.bslsk.midi.MidiMapper;
import com.bslsk.paint.PaintBrush;
import com.bslsk.paint.Painter;
import processing.core.PApplet;

public class GFrame extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener, WindowListener
{
	public static final String VERSION_ID = "0";
	//private static final long serialVersionUID = 1L;
	public Graphics2D buffer;
	public BufferedImage iB;
	public Color current;
	
	Thread t1;
	Random r1;
	public ArrayList<GContext> render;
	public double angle = 0;
	public double scale = 1;
	boolean hold = false;
	//public byte mode = 1;
	
	public int width;
	public int height;
	public double ratio;
	//final String[] presets = {"Regular","Double","2xDouble", "Quad","Glitch"}; 
	int colorMode = 2;
	//String[] cModes = {"Black","Random Color","ProgressiveColor"};
	
	
	FilterEffect filter;

	boolean sbtn; // shift key down
	//boolean drawingMode = false;
	//boolean mDown = false;
	//BufferedImage drawI;
	//Graphics2D drawG;
	
	//public boolean[] triggers = {false,false,false};
	
	KeyMapper keyMap;
	MidiMapper midiMap;
	
	//public Painter painter; //----------------------Create Paintmodes - > Implement
	
	//boolean imgMode;
	public PaintBrush brush;
	public boolean spoutActive;
	//Spout spout;
	MApplet spout;
	//boolean once;

	//MIDI
	KeyboardToSynth KB;
	MidiHandler mH;
	public GFrame(boolean spoutActive, MApplet sp)
	{
		super("MELTR");
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		spout = sp;
		//width = gd.getDisplayMode().getWidth();
		//height = gd.getDisplayMode().getHeight();
		width = 1920/2;
		height = 1080/2;
		Image img = Toolkit.getDefaultToolkit().createImage("res/logo.png");
		this.setIconImage(img);
		System.out.println(width + "         " + height);
		ratio = ((double)width)/((double)height);
		Assets.setGlobalConstants(width, height, ratio);
		Assets.getDefaultContraints();
		
		brush = new PaintBrush(width, height);
		
		//setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//setUndecorated(true);
		setSize(width,height);
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//side = new SideFrame();
		
		//ratio =  width/height;
		
		r1 = new Random();
		
		brush.dmC = Color.gray;
		//drawI = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
		//drawG = (Graphics2D)drawI.getGraphics();
		//drawG.setBackground(new Color(0,0,0,0));

		filter = new FilterEffect(FilterEffect.TYPE_EDGE, 1);
			
			
		
		iB = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		buffer = (Graphics2D)iB.getGraphics();
		buffer.setColor(Color.black);
		buffer.setBackground(Color.white);
		buffer.clearRect(0, 0, width, height);
		buffer.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		current = Color.white;
		//render = new ArrayList<GContext>();
		Assets.initRender();
		angle = 0;
		scale = 0;
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

		Assets.loadPainter("res/modes.txt");
		keyMap = new KeyMapper("res/keys.txt");
		midiMap = new MidiMapper("res/midi.txt");
		System.out.println();
		//lT = new LifeThread(width/10,height/10,250);
		//lThread =new Thread(lT);
		this.spoutActive = spoutActive;
        t1 = new Thread(this);
		t1.start();

		//MIDI
		mH = new MidiHandler();
		KB = new KeyboardToSynth();
		KB.run();
		repaint();

		
		
	}
	public void run()
	{
		while(true)
		{
			if(hold)
            {
                try
                {
                    //----------COLOR MODES----------
                    if(colorMode == 0)//static black
                        Assets.current = Color.black;
                    else if(colorMode == 1)//random
                    {
                        Assets.current = new Color(
                                Assets.current.getRed() + r1.nextInt(3)-1,
                                Assets.current.getGreen() + r1.nextInt(3)-1,
                                Assets.current.getBlue()+ r1.nextInt(3)-1
                        );
                    }
                    else if(colorMode == 2)//progressive
                    {
                        int n = r1.nextInt(3);
                        int n2 = r1.nextInt(7)-3;
                        if(n == 0 && (Assets.current.getRed() + n2 < 255 && Assets.current.getRed() + n2 > 0))
                                Assets.current = new Color(Assets.current.getRed() + n2, Assets.current.getGreen(), Assets.current.getBlue());
                        else if(n == 1 && (Assets.current.getGreen() + n2 < 255 && Assets.current.getGreen() + n2 > 0))
                                Assets.current = new Color(Assets.current.getRed() , Assets.current.getGreen()+ n2, Assets.current.getBlue());
                        else if(n == 2 && (Assets.current.getBlue() + n2 < 255 && Assets.current.getBlue() + n2 > 0))
                                Assets.current = new Color(Assets.current.getRed() , Assets.current.getGreen(), Assets.current.getBlue()+ n2);

                    }
				}
				catch(Exception e)
				{
								//System.out.println("NEW COLOR GENERATED");
				}
            }
			//finally {Assets.current = new Color(r1.nextInt(256),r1.nextInt(256),r1.nextInt(256));}
			if(hold)
			{

				for (GContext c : Assets.render)
					c.step();
				for(Recorder rc : Assets.recorders)
				{
					if(rc.isStarted())
						rc.updateRec();
					if(rc.isRunning())
						rc.act();
				}
				repaint();
				if (spoutActive)
				{
					try
					{
						spout.updateImage(iB);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}

			}
			try {Thread.sleep(1);}
			catch(InterruptedException ie) {}
		}
	}
	public void paint(Graphics g)
	{
		
		if(buffer == null){return;}
		/*
		for(Effect e : Assets.effects)
			if(e.isEnabled())
				e.doEffect(buffer, iB);
		*/


		buffer.setColor(Assets.current);
		for(GContext c : Assets.render)
			c.draw(buffer);

		buffer.drawImage(brush.getImage(), 0, 0, null);
		Assets.painter.update(this); // SEND TO PAINTER
	
		
		
		
		filter.doEffect(buffer, iB);

		g.drawImage(iB, 0, 0, this.getWidth(), this.getHeight(), null);

	}
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		System.out.println(e.getKeyChar() + "    " + e.getKeyCode());
		if(e.getKeyCode() == 81)
		{
			brush.setBrushColor(Color.red);
		}
		else if(e.getKeyCode() == 87)
		{
			brush.setBrushColor(Color.GREEN);
		}
		else if(e.getKeyCode() == 69)
		{
			brush.setBrushColor(Color.BLUE);
		}

		if(keyMap.keyPressed(e))
			return;

		if(e.getKeyCode() == KeyEvent.VK_SHIFT)
		{
			Assets.SHIFT = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE && !render.isEmpty())
		{	
			render.removeLast();
			//side.removeItem();
			return;
		}
		else if(e.getKeyChar() == ' ')
		{
			if(sbtn)
				Preferences.saveState();
			else
				hold = !hold;
		}
		else if(e.getKeyChar() == 'z') //TODO: Move to method within Assets
		{
			Assets.CONSTRAINTS[0].param = 0;

			Assets.CONSTRAINTS[1].param = 1;

			Assets.CONSTRAINTS[2].param = 0;

			Assets.CONSTRAINTS[3].param = 0;

			return;
		}
		else if(e.getKeyChar() == 'x')
		{
			buffer.clearRect(0, 0, 1000, 1000);
			render.clear();
			return;
		}
		else if(e.getKeyChar() == 'm') //Color mode for lines
		{
			colorMode ++;
			if(colorMode > 2)
				colorMode=0;
			//System.out.println("Color Mode: " + cModes[colorMode]);
			return;
		}
		else if(e.getKeyChar() == '/')//ENABLE brush.drawingMode
		{
			brush.drawingMode = !brush.drawingMode;
			//side.setbrush.drawingMode(brush.drawingMode);
			System.out.println("Drawing mode: " + (brush.drawingMode ? "ON":"OFF"));
		}
		else if(e.getKeyChar() == '.')//ENABLE brush.drawingMode
		{
			brush.imgMode = !brush.imgMode;
			//side.setbrush.drawingMode(brush.drawingMode);
			System.out.println("Image Mode: " + (brush.imgMode ? "ON":"OFF"));
		}
		else if(e.getKeyChar() == '0' && !Assets.CTRL)
			brush.colorchange[0] = !brush.colorchange[0];
		else if(e.getKeyCode() == KeyEvent.VK_MINUS && !Assets.CTRL)
			brush.colorchange[1] = !brush.colorchange[1];
		else if(e.getKeyCode() == KeyEvent.VK_EQUALS && !Assets.CTRL)
			brush.colorchange[2] = !brush.colorchange[2];
		else if(e.getKeyChar() == '0' && Assets.CTRL)
			brush.dmC = new Color(r1.nextInt(80)+126,10,10);
		else if(e.getKeyCode() == KeyEvent.VK_MINUS && Assets.CTRL)
			brush.dmC = new Color(10,r1.nextInt(80)+126,10);
		else if(e.getKeyCode() == KeyEvent.VK_EQUALS && Assets.CTRL)
			brush.dmC = new Color(10,10,r1.nextInt(80)+126);
		else if(e.getKeyCode() == KeyEvent.VK_QUOTE)
		{
			Assets.toggleTriggers();
		}
		//CONTROL KEY
		else if(e.getKeyCode() == KeyEvent.VK_CONTROL)
		{
			sbtn = true;
			System.out.println("Shift Down");
		}
		//FAILSAFE
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
				System.exit(1);
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			if(Assets.SHIFT)
			{
				System.out.println("Opening Command Frame");
				new CommandFrame(this, true);
			}
		}
	}



	@Override
	public void mouseDragged(MouseEvent e)
	{
		brush.paint(e);
	}
	@Override
	public void mouseMoved(MouseEvent e) {	
	}
	@Override
	public void mousePressed(MouseEvent e) {brush.mDown = true;}
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		brush.mDown = false;
		if(brush.drawingMode && !Assets.CTRL)
		{
			//buffer.drawImage(drawI, 0, 0, null);
			brush.drawG.clearRect(0, 0, width, height);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_SHIFT)
		{
			Assets.SHIFT = false;
		}
		if(keyMap.keyReleased(e, this))
			return;
		if(e.getKeyCode() == KeyEvent.VK_CONTROL)
		{
			sbtn = false;
			Assets.CTRL = false;
			System.out.println("Shift Up");
		}


	}
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	
	public static void main(String[] args) 
	{
		Object[] options = new Object[]{
				"Yes",
				"No"
		};
		int a = JOptionPane.showOptionDialog(null,"Enable Spout?","Starting Meltr",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[1]
				);
		if(a == 0)
		{
			MApplet applet = new MApplet();
			PApplet.runSketch(new String[]{""}, applet);
			GFrame g = new GFrame(true, applet);
		}
		else {
			GFrame g = new GFrame(false, null);
		}






		//new SplashScreen();
	//new GFrame();
	}

	@Override
	public void windowOpened(WindowEvent e)
	{

	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		mH.closeAll();
	}

	@Override
	public void windowClosed(WindowEvent e)
	{

	}

	@Override
	public void windowIconified(WindowEvent e)
	{

	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{

	}

	@Override
	public void windowActivated(WindowEvent e)
	{

	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{

	}
}
