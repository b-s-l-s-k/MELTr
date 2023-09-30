package com.bslsk.gen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class TrailContext extends GContext {
	Random ra;
	int ax,ay;
	int ar;
	int aS,aE;
	Color color;
	int width, height;
	public TrailContext(int w, int h, int x, int y, int r, int ss, int ee)
	{
		width = w;
		height = h;
		ax = x;
		ay = y;
		ar = r;
		aS = ss;
		aE = ee;
		ra = new Random();
	}
	@Override
	public void draw(Graphics2D g) 
	{
		//g.setColor(color);
		g.fillArc(ax, ay, ar, ar, aS, aE);
		
	}

	@Override
	public void step() 
	{
		int sC = ra.nextInt(10) - 5;
		ax += sC;
		ay += sC;
		ar += sC;
		aS += sC;
		aE += sC;
		if(ax < 0 || ay < 0 || ar < 0 )
			randomize();
		
	}
	public void randomize()
	{
		ax = ra.nextInt(width); 
		ay = ra.nextInt(height); 
		ar = ra.nextInt(75) + 75;
		aS = ra.nextInt(360);
		aE = ra.nextInt(360);
	}
	public static TrailContext randomContext(int w, int h)
	{
		Random r1 = new Random();
		int nR = r1.nextInt(75) + 75;
		return new TrailContext(w, h, r1.nextInt(w), r1.nextInt(h), nR, r1.nextInt(360),r1.nextInt(360));
	}
	

}
