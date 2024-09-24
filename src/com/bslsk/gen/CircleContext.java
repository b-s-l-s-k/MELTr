package com.bslsk.gen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import com.bslsk.info.Assets;

public class CircleContext extends GContext {

	int number;
	int radius;
	Color color;
	public CircleContext(int n, int r)
	{
		number = n;
		radius = r;
	}
	@Override
	public void draw(Graphics2D g) 
	{
		g.setColor(color);
		for(int x = 0; x < number; x ++)
		{
			int lx = Assets.R.nextInt(Assets.WIDTH);
			int ly = Assets.R.nextInt(Assets.HEIGHT);
			Stroke pS = g.getStroke();
			BasicStroke stroke = new BasicStroke((float)Math.abs(Assets.CONSTRAINTS[0].param), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
			g.setStroke(stroke);
			g.drawOval(lx, ly, radius, radius);
			g.setStroke(pS);
		}
		g.setColor(Assets.current);
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub

	}

	@Override
	public void modify(int amount)
	{

	}

	@Override
	public void addNext()
	{

	}



}
