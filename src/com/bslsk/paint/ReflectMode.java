package com.bslsk.paint;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.bslsk.bin.GFrame;
import com.bslsk.gen.*;
import com.bslsk.info.Assets;

public class ReflectMode extends PaintMode {

	@Override
	public void paintTo(GFrame g) 
	{
		for(int x = 0; x < g.getWidth()/2; x += g.width/(g.ratio*4))
			for(int y = 0; y < g.getHeight();y+=g.height/4)
			{
				BufferedImage n = g.iB.getSubimage(x, y,g.width/(int)(g.ratio*4), g.height/4);
				Graphics2D nG = (Graphics2D)n.getGraphics();
				if(g.triggers[0])
					nG.scale(Assets.CONSTRAINTS[1].param,Assets.CONSTRAINTS[1].param);
				if(g.triggers[1])
					nG.translate(Assets.CONSTRAINTS[2].param, Assets.CONSTRAINTS[3].param);
				if(g.triggers[2])
					nG.rotate(Math.toRadians(Assets.CONSTRAINTS[0].param));
				
				
				nG.drawImage(n, 0, 0, g.width/(int)(g.ratio*4), g.height/4, null);
				if(g.triggers[1])
					g.buffer.drawImage(n, x+(int)Assets.CONSTRAINTS[2].param, y+(int)Assets.CONSTRAINTS[3].param, g.width/(int)(g.ratio*4), g.height/4, null);
				else
					g.buffer.drawImage(n, x, y, g.width/(int)(g.ratio*4), g.height/4, null);
			}
		//System.out.println(Assets.CONSTRAINTS[0].param + "------------");
		//System.out.println(Assets.CONSTRAINTS[1].param + "------------");
		//System.out.println(Assets.CONSTRAINTS[2].param + "------------");
		//System.out.println(Assets.CONSTRAINTS[3].param + "------------");
		g.buffer.drawImage(g.iB, g.width, 0, g.width/2, g.height, 0, 0, g.width/2, g.height, null);
		
	}

}
