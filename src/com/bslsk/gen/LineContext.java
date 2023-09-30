package com.bslsk.gen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class LineContext extends GContext {

	ArrayList<Point> points;
	Random r1;
	public LineContext(int ps, int w, int h) {
		super();
		r1 = new Random();
		points = new ArrayList<Point>();
		for(int x = 0; x < ps; x ++)
		{
			points.add(new Point(r1.nextInt(w),r1.nextInt(h)));
		}
	}

	@Override
	public void draw(Graphics2D g) {
		Color c = g.getColor();
		g.setColor(Color.black);
		for(int x = 0; x < points.size()-1; x ++)
		{
			g.drawLine(points.get(x).x, points.get(x).y, points.get(x+1).x, points.get(x+1).y);
		}
		g.drawLine(points.get(0).x, points.get(0).y, points.get(points.size()-1).x, points.get(points.size()-1).y);
		g.setColor(c);
	}

	@Override
	public void step() {
		for(int x = 0; x < points.size(); x ++)
		{
			points.get(x).setLocation((int)points.get(x).x + (r1.nextInt(5)-2), (int)points.get(x).y +(r1.nextInt(5)-2 ));
		}

	}

}
