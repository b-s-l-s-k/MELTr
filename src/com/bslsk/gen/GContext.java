package com.bslsk.gen;

import java.awt.Graphics2D;

public abstract class GContext 
{
	public GContext()
	{
		
	}
	public abstract void draw(Graphics2D g);
	
	public abstract void step();
	
}
