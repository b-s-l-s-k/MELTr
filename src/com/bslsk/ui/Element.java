package com.bslsk.ui;

import java.awt.Color;

public class Element 
{
	public static final Color DEFAULT_COLOR = Color.red;
	
	int x,y;
	int width,height; // may not be necessary?
	Color color;
	public Element(int nx, int ny, int w, int h)
	{
		x = nx;
		y = ny;
		width = w;
		height = h;
		color = DEFAULT_COLOR;
	}
}
