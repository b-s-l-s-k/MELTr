package com.bslsk.ui;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Section extends Element
{
	ArrayList<Element> elements;
	public Section(int nx, int ny, int w, int h) 
	{
		super(nx, ny, w, h);
		elements = new ArrayList<Element>();
	}
	public void addElement(Element e)
	{
		elements.add(e);
	}
	public void removeElement(Element e)
	{
		elements.remove(e);
	}
	public void removeElement(int i)
	{
		elements.remove(i);
	}
	public void removeLastElement(Element e)
	{
		elements.remove(elements.size()-1);
	}
	
	@Override
	public void clicked(MouseEvent e)
	{
		for(Element el: elements)
			if(el.isInside(e))
				el.clicked(e);
	}
	
	
}
