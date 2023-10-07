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
	public ArrayList<MidiKey> keys;
	public String file;
	public MidiMapper(String f)
	{
		file = f;
		keys = new ArrayList<MidiKey>();
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
					keys.add(new MidiKey(n     , new Action(x   , x2,    x3)));
					System.out.println(keys.get(keys.size()-1).toString());
				}
				else
				{
					s1.nextInt();s1.nextInt();s1.nextInt();
				}
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public boolean keyPressed(KeyEvent k, GFrame g)
	{
		for(MidiKey k2: keys)
			if(k2.key == k.getKeyCode())
			{
				k2.pressed(g);
				return true;
			}
		return false;
	}
	public boolean keyReleased(KeyEvent k, GFrame g)
	{
		for(MidiKey k2: keys)
			if(k2.key == k.getKeyCode())
			{
				k2.released(g);
				return true;
			}
		return false;
	}
}
