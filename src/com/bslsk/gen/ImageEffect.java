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
	int x,y;
	int xScale, yScale;
	int rotation;
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
		g.rotate(rotation);
		g.scale(xScale, yScale);
		g.drawImage(img[active_img], x, y, img[active_img].getWidth(), img[active_img].getHeight(), null);

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
