package com.bslsk.paint;

import com.bslsk.bin.GFrame;

public class Painter 
{
	//Modes
	PaintMode[] modes;
	//Mode Links
	//---4 links to 2 // index is "GFrame.mode" set negative if effect should be run before, or after (+)
	//Before/After?
	int[] links;
	int active;
	public Painter()
	{
		
	}
	public Painter(PaintMode[] m, int[] l)
	{
		modes = m;
		links = l;
		active = 0;
	}
	public void update(GFrame g)
	{
		if(links[active] != active && links[active] < 0)
			modes[Math.abs(links[active])].paintTo(g);
		
		/*else if(links[active] == active)*/
			modes[active].paintTo(g);
			
		if(links[active] != active && active > 0)
			modes[Math.abs(links[active])].paintTo(g);
	}
	public void setActive(int i)
	{
		active = i;
	}
	
	
	
	
}
