package com.bslsk.ui;

import java.awt.event.MouseEvent;

public class MButton extends Element
{
	
	public float x_weight;
	public float y_weight;
	public int padding;
	
	public int key;
	public String word;
	public MButton()
	{
		super(0,0,0,0);
		padding = 0;
		x_weight = 0;
		y_weight = 0;
		word = "";
		key = 0;
	}
	public MButton(int x2, int y2, int scale,int p,String w, int k)
	{
		super(x2,y2,scale,scale);
		padding = p;
		x_weight = 1;
		y_weight = 1;
		word = w;
		key = k;
	}
	public void setWeight(float xw, float yw)
	{
		x_weight = xw;
		y_weight = yw;
	}
	public int getAdjustedSizeX()
	{
		return (int)(width * x_weight);
	}
	@Override
	public boolean isInside(MouseEvent e) 
	{
		int ex = e.getX();
		int ey = e.getY();
		
		if(ex < x + getAdjustedSizeX() && ex > x)
			if(ey < y + height && ey > y)
				return true;
		return false;
	}
	public void start() 
	{
		
		
	}
	@Override
	public String toString()
	{
		return word + ": " + key + "   x:"+ x + "    y:" +y;
	} 
}
