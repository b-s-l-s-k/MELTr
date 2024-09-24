package com.bslsk.gen;

import java.awt.Graphics2D;
import java.util.Random;

public class ClearContext extends GContext {

	Random r1;
	int width,height;
	int size;
	public ClearContext(int w, int h) {
		super();
		 r1 = new Random();
		 width = w;
		 height = h;
		 size = 50;
	}

	@Override
	public void draw(Graphics2D g) {
		g.clearRect(r1.nextInt(width), r1.nextInt(height),size,size);

	}

	@Override
	public void step() {
		// TODO Auto-generated method stub

	}

	@Override
	public void modify(int amount)
	{
		size = amount;
	}

	@Override
	public void addNext()
	{

	}

	@Override
	public boolean removeLast()
	{
		return false;
	}

}
