package com.bslsk.info;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.bslsk.bin.GFrame;

public class KeyMapper 
{
	//DEFAULT
	ArrayList<Key> keys;
	private String fileL;
	public KeyMapper()
	{
		
	}
	// W/ PATH 
	public KeyMapper(String file)
	{
		fileL = file;
		keys = new ArrayList<Key>();
		loadFile();
	}
	private void loadFile()
	{
		FileInputStream fis;
		try {
			File f = new File(fileL);
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
					keys.add(new Key(n     , new Action(x   , x2,    x3)));
					System.out.println(keys.get(keys.size()-1).toString());
				}
				else
				{
					int c = s1.nextInt();
					System.out.println("NUM ATTR: " + c);
					Action[] act = new Action[c];
					for(int i = 0; i < c; i++)
						act[i] = new Action(s1.nextInt(),s1.nextInt(),s1.nextInt());
					keys.add(new MultiKey(Math.abs(n)     , act));
					System.out.println("MULTI KEY:  " + c + " mapped attributes");
				}
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public boolean keyPressed(KeyEvent k, GFrame g)
	{
		for(Key k2: keys)
			if(k2.key == k.getKeyCode())
			{
				k2.pressed(g);
				return true;
			}
		return false;
	}
	
}
