package com.bslsk.gen;

import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

import com.bslsk.info.Assets;

public class ImageContext extends GContext {

	int x,y;
	int width, height;
	BufferedImage img;
	Shifter[] shifters;
	public ImageContext(String file)
	{
		x = Assets.WIDTH/10;
		y = Assets.HEIGHT/10;
		img = (BufferedImage)Toolkit.getDefaultToolkit().createImage(file);
		width = img.getWidth();
		height = img.getHeight();
	}
	@Override
	public void draw(Graphics2D g) 
	{
		g.drawImage(img, x, y, width, height, null);

	}

	@Override
	public void step() 
	{
		for(Shifter s: shifters)
		{
			if(s.type == Shifter.TYPE_TRAN_X)
				x = (int)s.shift(x);
			else if(s.type == Shifter.TYPE_TRAN_Y)
				y = (int)s.shift(y);
			else if(s.type == 4) // width
				width = (int)s.shift(width);
			else if(s.type == 5) // height
				height = (int)s.shift(height);
		}

	}
	private void loadFile(String fileL)
	{
		FileInputStream fis;
		try {
			File f = new File(fileL);
			f = f.getAbsoluteFile();
			fis = new FileInputStream(f);
			Scanner s1 = new Scanner(fis);
		}catch(Exception e) {}
	}

}
