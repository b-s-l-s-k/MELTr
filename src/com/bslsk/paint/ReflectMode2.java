package com.bslsk.paint;

import com.bslsk.bin.GFrame;
import com.bslsk.info.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ReflectMode2 extends PaintMode
{

	@Override
	public void paintTo(GFrame g) 
	{
		double maxX = ((double)g.width)/(g.ratio*4.0);
		double maxY = (g.height/4);
		System.out.println("==========" + maxX + "   ||   " + maxY);
		/*
		for(double x = 0; x < g.width/2.0; x += ((double)g.width)/(g.ratio*4.0))
			for(int y = 0; y <= g.height;y+=(g.height/4))
			*/
		for(double x = 0; x < 4; x ++)
			for(int y = 0; y <  4;  y++)
			{
				if(y + (g.height/4) >= g.height)
					break;
				
				System.out.println(y);
				BufferedImage n = g.iB.getSubimage((int)x*120, y*120,120,120);
				Graphics2D nG = (Graphics2D)n.getGraphics();

				nG.rotate(Math.toRadians(Assets.CONSTRAINTS[0].param));
				nG.scale(Assets.CONSTRAINTS[1].param,Assets.CONSTRAINTS[1].param);
				if(Assets.triggers[1])
					nG.translate(Assets.CONSTRAINTS[2].param, Assets.CONSTRAINTS[3].param);


				
				
				nG.drawImage(n, 0, 0, g.width/(int)(g.ratio*4), g.height/4, null);

				if(Assets.triggers[1])
					g.buffer.drawImage(n, (int)x+(int)Assets.CONSTRAINTS[2].param, y+(int)Assets.CONSTRAINTS[3].param, g.width/(int)(g.ratio*4), g.height/4, null);
				else
					g.buffer.drawImage(n, (int)x*120, y*120, 120, 120, null);
			}
		g.buffer.drawImage(g.iB, g.width, 0, g.width/2, g.height, 0, 0, g.width/2, g.height, null);
		
	}

}
