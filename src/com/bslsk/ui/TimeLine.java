package com.bslsk.ui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class TimeLine 
{
	int width;
	int height;
	BufferedImage ui;
		Graphics2D uiG;
	THeader header;
	//GRID DATA
	int nRows, nCols;
	int cellW, cellH;
	
	ArrayList<Element> elements;
	int scaleX,scaleY;
	public TimeLine(Graphics g, int w, int h) 
	{
		width = w;
		height = h;
		//ui = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		//uiG = (Graphics2D)ui.getGraphics();
		uiG = (Graphics2D)g;
		header = new THeader(0,10,width,20);
		elements = new ArrayList<Element>();
		
	}
	public void update()
	{
		//draw Background
		
		//draw Grid Lines
		
		//Draw elements
		
		//draw labels
		
		//drawHeader
		header.drawHeader(uiG);
	}
	
	public void mouseButton(MouseEvent m, int type)
	{
		if(type == M_CLICKED)
		{
			
		}
		else if(type == M_PRESSED)
		{
			
		}
		else if(type == M_RELEASED)
		{
			
		}
		else if(type == M_DRAGGED)
		{
			
		}
		update();
	}
	public static final int CMD_KEY = 0, REG_KEY = 1, NUM_KEY = 2;
	public void keyUpdate(KeyEvent k, int t)
	{
		
	}
	public void mouseWheel(MouseWheelEvent m)
	{
		update();
	}
	
	
	public static final int M_CLICKED = 0 , M_PRESSED = 1, M_RELEASED = 2, M_DRAGGED = 3;
	public void updateComps(int w, int h) 
	{
		width = w;
		height = h;
		header.width = width;
		header.height = height;
	}
}
