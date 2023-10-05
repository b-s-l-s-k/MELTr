package com.bslsk.gen;

import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class ImageEffect extends Effect {

	boolean active;
	int active_img;
	BufferedImage[] img;
	public int x,y;
	public double xScale, yScale;
	public int rotation;
	public ImageEffect()
	{
		
		File folder = new File("res/img");
		String[] files = folder.list();
		
		img = new BufferedImage[files.length];
		int c = 0;
		for(String file: files)
		{
			file = "./res/img/"+file;
			try {
				img[c] = ImageIO.read(new File(file));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		x = 10; y = 10;
		xScale = 1;
		yScale = 1;
		rotation = 0;
		
	}
	@Override
	public void doEffect(Graphics2D g, BufferedImage i) 
	{
		Graphics2D b = (Graphics2D)img[active_img].getGraphics();
		b.rotate(Math.toRadians(rotation));
		//g.scale(xScale, xScale);
		
		b.drawImage(img[active_img], 0, 0, null);
		g.drawImage(img[active_img], x, y, (int)(img[active_img].getWidth()*xScale), (int)(img[active_img].getHeight()*xScale), null);
		g.rotate(Math.toRadians(rotation));
		//g.scale(xScale, xScale);

	}

	@Override
	public void toggle() {
		active = !active;

	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return active;
	}

}
