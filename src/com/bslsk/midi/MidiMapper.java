package com.bslsk.midi;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import com.bslsk.bin.GFrame;
import com.bslsk.info.Action;


public class MidiMapper 
{
	public MidiAction[] keys;
	public String file;
	public MidiMapper(String f)
	{
		file = f;
		keys = new MidiAction[128];
		loadFile();
	}
	/**
	 * Load the file associated with this instance of KeyMapper
	 */
	private void loadFile()
	{
		FileInputStream fis;
		try {
			File f = new File(file);
			f = f.getAbsoluteFile();
			fis = new FileInputStream(f);
			Scanner s1 = new Scanner(fis);
			while(s1.hasNext()) // LAYOUT: [keychar] [type] [sType] [dType] 
			{
				
				int n = s1.nextInt(); 
				if(n > 0)
				{
					int x = s1.nextInt();
					int x2 = s1.nextInt();
					int x3 = s1.nextInt();
					keys[n] = new MidiAction(x   , x2,    x3);
					System.out.println("MIDI ACTION CREATED -> " + x + " " + x2 + " " + x3);
				}
				else
				{
					s1.nextLine();
				}
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void eval(int cont, int value)
	{
		if(keys[cont] != null)
			keys[cont].act(value);
	}
}
