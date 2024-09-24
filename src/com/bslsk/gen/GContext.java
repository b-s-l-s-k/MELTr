package com.bslsk.gen;

import com.bslsk.util.ColorStack;

import java.awt.Graphics2D;

public abstract class GContext 
{
	public GContext next;
	ColorStack colorStack;
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
	public void popColor()
	{
		colorStack.popColor();
		if(next != null)
			next.popColor();
	}

	public void pushColor()
	{
		colorStack.pushColor();
		if(next != null)
			next.pushColor();
	}
}
