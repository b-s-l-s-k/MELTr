package com.bslsk.gen;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Effect 
{
	public Effect()
	{
		
	}
	public abstract void doEffect(Graphics2D g, BufferedImage i);
	public abstract void toggle();
	public abstract boolean isEnabled();
	public abstract void alter(int setting, int value);
	
}
