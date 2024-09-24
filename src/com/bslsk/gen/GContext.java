package com.bslsk.gen;

import java.awt.Graphics2D;

public abstract class GContext 
{
	public GContext next;
	public GContext()
	{
		
	}
	public abstract void draw(Graphics2D g);
	
	public abstract void step();

	public abstract void modify(int amount);

	public abstract void addNext();

	public boolean removeLast()
	{
		if(next == null)
			return true;
		else if(next.removeLast())
			next = null;
		return false;
	}
}
