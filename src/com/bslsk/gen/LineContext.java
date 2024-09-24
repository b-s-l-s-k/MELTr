package com.bslsk.gen;

import com.bslsk.info.Assets;
import com.bslsk.util.ColorStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class LineContext extends GContext {

	ArrayList<Point> points;
	Random r1;
	int jitter;
	ColorStack colorStack;
	int width, height;
	public LineContext(int ps, int w, int h) {
		super();
		width = w;
		height = h;
		r1 = new Random();
		points = new ArrayList<Point>();
		for(int x = 0; x < ps; x ++)
		{
			points.add(new Point(r1.nextInt(w),r1.nextInt(h)));
		}
		jitter = ps;
		colorStack = new ColorStack();
	}

	@Override
	public void draw(Graphics2D g) {
		Color c = g.getColor();
		g.setColor(Color.black);
		float gt = ((BasicStroke)g.getStroke()).getLineWidth();
		//System.out.println(gt + " thickness");
		g.setStroke(new BasicStroke(5));
		for(int x = 0; x < points.size()-1; x ++)
		{
			//g.setColor(Color.white);
			//g.setStroke(new BasicStroke(10));
			//g.drawLine(points.get(x).x, points.get(x).y, points.get(x+1).x, points.get(x+1).y);
			g.setColor(colorStack.getTop());
			g.setStroke(new BasicStroke(5));
			g.drawLine(points.get(x).x, points.get(x).y, points.get(x+1).x, points.get(x+1).y);
		}
		g.drawLine(points.getFirst().x, points.getFirst().y, points.getLast().x, points.getLast().y);
		if(next != null)
			next.draw(g);
		g.setColor(c);
	}

	@Override
	public void step() {
		for(int x = 0; x < points.size(); x ++)
		{
			points.get(x).setLocation((int)points.get(x).x + (r1.nextInt(jitter)-(jitter/2)), (int)points.get(x).y +(r1.nextInt(jitter)-(jitter/2) ));
				if(points.get(x).x < 0 || points.get(x).x > Assets.WIDTH || points.get(x).y < 0 || points.get(x).y > Assets.HEIGHT)
				{
					points.get(x).x = Assets.WIDTH / 2;
					points.get(x).y = Assets.HEIGHT/2;
				}
		}
		if(next != null)
			next.step();

	}

	@Override
	public void modify(int amount)
	{

		jitter = amount + 1;
		if(next != null)
			next.modify(amount);
		//System.out.println("Line Jitter Amount: " + jitter);
	}

	@Override
	public void addNext()
	{
		System.out.println("Creating Next Trail");
		if(next == null && points.size()> 1)
		{
			next = new LineContext(points.size()-1,width,height);
			//((LineContext)next).colorStack = colorStack;
			System.out.println("LINE ADDITION FAILED");
		}
		else
			next.addNext();
	}




	public void popColor()
	{
		colorStack.popColor();
		if(next != null)
			((LineContext)next).popColor();
	}

	public void pushColor()
	{
		colorStack.pushColor();
		if(next != null)
			((LineContext)next).pushColor();
	}
}
