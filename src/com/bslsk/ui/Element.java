package com.bslsk.ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Element 
{
	public static final Color DEFAULT_COLOR = Color.red;
	
	public int x,y;
	public int width,height; // may not be necessary?
	Color color;
	
	BufferedImage self;
	public Element(int nx, int ny, int w, int h)
	{
		x = nx;
		y = ny;
		width = w;
		height = h;
		color = DEFAULT_COLOR;
	}
	public void clicked(MouseEvent e)
	{
		
	}
	public boolean isInside(MouseEvent e)
	{
		if(e.getX() < x + width && e.getX() > x && e.getY() < y + height && e.getY() > y)
			return true;
		else
			return false;
	}
	public void updateUI()
	{
		
	}
	public BufferedImage getUI()
	{
		return self;
	}
}
