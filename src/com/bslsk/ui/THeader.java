package com.bslsk.ui;

import java.awt.Graphics;

import com.bslsk.info.Preferences;

public class THeader 
{
	int start = 0;
	int end = 10;
	int[] zone;
	int spacing = 50;
	
	int width = 500, height=20;
	
	public THeader()
	{
		
	}
	public THeader(int s, int e, int w, int h)
	{
		start = s;
		end = e;
		
		width = w;
		height = h;
		spacing = width/(end-start);
		
		zone = new int[]{start,end};
	}
	public void drawHeader(Graphics g)
	{
		g.setColor(Preferences.headerColor);
		g.fillRect(0, 0, width, height);
		g.setColor(Preferences.headerFontColor);
		for(int x = 0; x < width; x += spacing)
		{
			g.drawLine(x, 0, x, height/4);
		}
	}
	
	//-----------ZONING-------------
	public void scrollZone(int amt)
	{
		if(zone[0]+amt >= start && zone[1]+amt <= end)
		{
			zone[0] += amt;
			zone[1] += amt;
		}
	}
	public static final boolean ZOOM_IN = false, ZOOM_OUT=true;
	public void zoomZone(int amt, boolean zType)
	{
		if(zType)
		{
			if(zone[0]-amt >= start && zone[1]+amt <= end)
			{
				zone[0] -= amt;
				zone[1] += amt;
			}
		}
		else
		{
			if(zone[0]+amt >= start && zone[1]-amt <= end && (zone[1]-amt) - (zone[0]+amt) >=1)
			{
				zone[0] += amt;
				zone[1] -= amt;
			}
		}
	}
}
