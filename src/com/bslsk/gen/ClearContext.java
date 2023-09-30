package com.bslsk.gen;

import java.awt.Graphics2D;
import java.util.Random;

public class ClearContext extends GContext {

	Random r1;
	int width,height;
	public ClearContext(int w, int h) {
		super();
		 r1 = new Random();
		 width = w;
		 height = h;
	}

	@Override
	public void draw(Graphics2D g) {
		g.clearRect(r1.nextInt(1000), r1.nextInt(1000),50,50);

	}

	@Override
	public void step() {
		// TODO Auto-generated method stub

	}

}
