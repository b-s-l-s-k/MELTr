package com.bslsk.gen;

import com.bslsk.info.Assets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class ColorContext extends GContext {
	Random r1;
	int amount;

	int width, height;

	public ColorContext(int amt, int w, int h) {
		super();
		r1 = new Random();
		amount  = amt;
		width = w;
		height = h;
	}

	@Override
	public void draw(Graphics2D g) {
		//cc = new Color(r1.nextInt(256),r1.nextInt(256),r1.nextInt(256));
		//g.setColor(cc);
		for(int x = 0; x < amount; x ++)
		{
			/*
			if(r1.nextBoolean())
				cc.brighter();
			else
				cc.darker();
				*/
			int mx = r1.nextInt(width);
			int my = r1.nextInt(height);
			g.fillRect(mx, my, Assets.WIDTH/10, Assets.WIDTH/10);
		}

	}

	@Override
	public void step() {
		// TODO Auto-generated method stub

	}

	@Override
	public void modify(int amount)
	{
		this.amount = amount;
	}

	@Override
	public void addNext()
	{

	}



}
