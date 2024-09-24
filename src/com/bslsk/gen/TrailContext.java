package com.bslsk.gen;

import com.bslsk.info.Assets;

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
		//ra = new Random();
		color = new Color(Assets.R.nextInt(255),Assets.R.nextInt(255),Assets.R.nextInt(255));
	}
	@Override
	public void draw(Graphics2D g) 
	{
		g.setColor(color);
		g.fillArc(ax, ay, ar, ar, aS, aE);
		if(next != null)
			next.draw(g);
		
	}

	@Override
	public void step() 
	{
		int sC = Assets.R.nextInt(10) - 5;
		ax += sC;
		ay += sC;
		ar += sC;
		aS += sC;
		aE += sC;
		if(ax < 0 || ay < 0 || ar < 0 )
			randomize();
		if(next != null)
			next.step();

		//color = new Color(Assets.R.nextInt(255),Assets.R.nextInt(255),Assets.R.nextInt(255));
	}

	@Override
	public void modify(int amount)
	{
		ar = amount;
	}

	@Override
	public void addNext()
	{
		System.out.println("Creating Next Trail");
		if(next == null)
		{
			next = new TrailContext(width, height, ax, ay, ar, aS, aE);
			randomize();
		}
		else
			next.addNext();
	}



	public void randomize()
	{
		ax = Assets.R.nextInt(width); 
		ay = Assets.R.nextInt(height); 
		ar = Assets.R.nextInt(75) + 75;
		aS = Assets.R.nextInt(360);
		aE = Assets.R.nextInt(360);
	}
	public static TrailContext randomContext(int w, int h)
	{
		Random r1 = new Random();
		int nR = r1.nextInt(75) + 75;
		return new TrailContext(w, h, r1.nextInt(w), r1.nextInt(h), nR, r1.nextInt(360),r1.nextInt(360));
	}
	

}
