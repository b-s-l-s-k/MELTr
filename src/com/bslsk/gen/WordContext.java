package com.bslsk.gen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import com.bslsk.info.Action;
import com.bslsk.info.Assets;
import com.bslsk.info.Key;

public class WordContext extends GContext {

	ArrayList<String> words;
	Shifter s;
	int x,y;
	int current = 0;
	public WordContext(String f)
	{
		loadFile(f);
		s = new Shifter(Shifter.TYPE_TRAN_Y, Shifter.I_PINGPONG, 1, 10, Assets.HEIGHT-50);
		x = Assets.WIDTH/5;
		y = 10;
		s.toggleActive();
	}
	@Override
	public void draw(Graphics2D g) 
	{
		g.setColor(Color.black);
		g.setFont(Assets.drawFont);
		g.drawString(words.get(current), x, y);
		g.setColor(Assets.current);
	}

	@Override
	public void step() 
	{
		y = (int)s.shift(y);

	}
	private void loadFile(String fileL)
	{
		words = new ArrayList<String>();
		FileInputStream fis;
		try {
			File f = new File(fileL);
			f = f.getAbsoluteFile();
			fis = new FileInputStream(f);
			Scanner s1 = new Scanner(fis);
			while(s1.hasNext()) // LAYOUT: [keychar] [type] [sType] [dType] 
			{
				words.add(s1.next());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}
