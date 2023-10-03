package com.bslsk.bin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class SplashScreen extends JFrame implements Runnable
{
	BufferedImage img;
	boolean done;
	Thread t1;
	int countdown;
	public SplashScreen()
	{
		super("MELTr");
		//JFrame.setDefaultLookAndFeelDecorated(true);
		setUndecorated(true);
		setBackground(new Color(0,255,0,0));
		//this.setOpacity(0.55f);

		//setShape(new Ellipse2D.Double(0,0,getWidth(),getHeight()));
		img =null;
		try {
		    img = ImageIO.read(new File("res/splashscreen.png"));
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		setSize(img.getWidth()/2,img.getHeight()/2);
		this.setLocation(200, 200);
		countdown = 0;
		setVisible(true);
		t1 = new Thread(this);
		t1.start();
		repaint();
		//this.getContentPane().getGraphics().drawImage(img, 0, 0, 500, 500, null);
		
	}
	public void run()
	{
		while(!done)
		{
			try 
			{
				countdown ++;
				if(countdown >= 100)
					done = true;
				repaint();
				Thread.sleep(20);
			}
			catch(Exception e) {}
		}
		new GFrame();
		this.setVisible(false);
	}
	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(img, 0, 0, img.getWidth()/2, img.getHeight()/2, null);
		g2.setColor(Color.cyan);
		int max = getWidth()-15;
		int r = (int)((double)max * ((double)countdown/(double)100.0));
		g2.fillRect(10,(getHeight()/10)*8,r,30);
		System.out.println(countdown+"");
	}
}
