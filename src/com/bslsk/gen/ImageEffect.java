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
				System.out.println(file.toString());
				c++;
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
		//Graphics2D b2 = (Graphics2D)img[active_img].getGraphics();
		//BufferedImage backup = img[active_img].clone();
		BufferedImage save = new BufferedImage(img[active_img].getWidth(), img[active_img].getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D b = (Graphics2D)save.getGraphics();
		//b.drawImage(img[active_img], 0, 0, null);
		b.rotate(Math.toRadians(rotation),img[active_img].getWidth()/2, img[active_img].getHeight()/2);
		b.drawImage(img[active_img], 0, 0, null);
		//b2.drawImage(save, 0, 0, null);
		//g.scale(xScale, xScale);
		//b.
		
		g.drawImage(save, x, y, (int)(img[active_img].getWidth()*xScale), (int)(img[active_img].getHeight()*xScale), null);
		//b.rotate(Math.toRadians(-1*rotation));
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
	@Override
	public void alter(int setting, int value) 
	{
		active_img ++;
		if(active_img >= img.length)
			active_img = 0;
		System.out.println(active_img+ " = ACTIVE IMG");
	}
	public BufferedImage getActiveImage()
	{
		return img[active_img];
	}
	
	public BufferedImage getAsPen(boolean rot, boolean sca)
	{
		BufferedImage save = new BufferedImage(img[active_img].getWidth(), img[active_img].getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D b = (Graphics2D)save.getGraphics();
		//b.drawImage(img[active_img], 0, 0, null);
		if(rot)
			b.rotate(Math.toRadians(rotation),img[active_img].getWidth()/2, img[active_img].getHeight()/2);
		
		b.drawImage(img[active_img], 0, 0, null);
		return save;
	}

}
