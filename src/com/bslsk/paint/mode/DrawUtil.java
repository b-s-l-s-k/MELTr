package com.bslsk.paint.mode;

import java.awt.Color;

import com.bslsk.bin.GFrame;

public class DrawUtil 
{
	private GFrame frame;
	public DrawUtil(GFrame f)
	{
		frame = f;
	}
	public void drawRect(int x, int y, int width, int height, Color c)
	{
		frame.buffer.setColor(c);
		drawRect(x, y, width, height);
	}
	public void drawRect(int x, int y, int width, int height)
	{
		frame.buffer.drawRect(x, y, width, height);
	}
	public void fillRect(int x, int y, int width, int height, Color c)
	{
		frame.buffer.setColor(c);
		fillRect(x, y, width, height);
	}
	public void fillRect(int x, int y, int width, int height)
	{
		frame.buffer.fillRect(x, y, width, height);
	}
}
