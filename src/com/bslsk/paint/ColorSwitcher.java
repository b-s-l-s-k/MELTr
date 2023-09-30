package com.bslsk.paint;

import java.awt.Color;
import java.util.Random;

public class ColorSwitcher 
{
	
	ColorScheme[] colors;
	int active;
	/**
	 * @param n the number of colors to store in an array
	 */
	public ColorSwitcher(int n)
	{
		colors = new ColorScheme[n];
	}
	public ColorScheme randomScheme()
	{
		Random r1 = new Random();
		return new ColorScheme(r1.nextInt(3),r1.nextInt(3),r1.nextInt(3));
	}
	public void setActive(int a)
	{
		active = a;
	}
	public void updateActive()
	{
		colors[active].update();
	}
}
