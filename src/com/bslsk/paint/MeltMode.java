package com.bslsk.paint;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.bslsk.bin.GFrame;
import com.bslsk.info.Assets;

public class MeltMode extends PaintMode {

	@Override
	public void paintTo(GFrame g) 
	{
		/*
		if(g.triggers[0])
			nG.scale(Assets.CONSTRAINTS[1].param,Assets.CONSTRAINTS[1].param);
		if(g.triggers[1])
			nG.translate(Assets.CONSTRAINTS[2].param, Assets.CONSTRAINTS[3].param);
		if(g.triggers[2])
			nG.rotate(Math.toRadians(Assets.CONSTRAINTS[0].param));
		*/
		BufferedImage[] quads = new BufferedImage[4];
		quads[0] = g.iB.getSubimage(0, 0,g.width/2, g.height/2);
		quads[1] = g.iB.getSubimage(g.width/2, 0,g.width/2, g.height/2);
		quads[2] = g.iB.getSubimage(0, g.height/2,g.width/2, g.height/2);
		quads[3] = g.iB.getSubimage(g.width/2, g.height/2,g.width/2, g.height/2);
		for(int x = 0; x < quads.length; x ++)
			paintRecursive(g,quads[x], 3);
		//g.buffer.drawImage(g.iB, g.width, 0, g.width/2, g.height, 0, 0, g.width/2, g.height, null);
		g.buffer.drawImage(quads[0],0, 0,g.width/2, g.height/2,null);
		g.buffer.drawImage(quads[1],g.width/2, 0,g.width/2, g.height/2,null);
		g.buffer.drawImage(quads[2],0, g.height/2,g.width/2, g.height/2,null);
		g.buffer.drawImage(quads[3],g.width/2, g.height/2,g.width/2, g.height/2,null);
		//Graphics2D nG = (Graphics2D)n.getGraphics();
		
	}
	private void paintRecursive(GFrame g, BufferedImage quad, int count)
	{
		BufferedImage[] quads2 = new BufferedImage[4];
		quads2[0] = quad.getSubimage(0, 0,quad.getWidth()/2, quad.getHeight()/2);
		quads2[1] = quad.getSubimage(quad.getWidth()/2, 0,quad.getWidth()/2, quad.getHeight()/2);
		quads2[2] = quad.getSubimage(0, quad.getHeight()/2,quad.getWidth()/2, quad.getHeight()/2);
		quads2[3] = quad.getSubimage(quad.getWidth()/2, quad.getHeight()/2,quad.getWidth()/2, quad.getHeight()/2);
		if(count > 0)
			for(int x = 0; x < quads2.length; x ++)
				paintRecursive(g,quads2[x], count-1);
		
		for(int x = 0; x < quads2.length; x ++)
		{
			Graphics2D nG = (Graphics2D)quads2[x].getGraphics();
			if(x % 2 == 0)
			{
				if(Assets.triggers[0])
					nG.scale(Assets.CONSTRAINTS[1].param,Assets.CONSTRAINTS[1].param);
				if(Assets.triggers[1])
					nG.translate(Assets.CONSTRAINTS[2].param, Assets.CONSTRAINTS[3].param);
				if(Assets.triggers[2])
					nG.rotate(Math.toRadians(Assets.CONSTRAINTS[0].param));
			}
			else
			{
				if(Assets.triggers[0])
					nG.scale(Assets.CONSTRAINTS[1].param,Assets.CONSTRAINTS[1].param);
				if(Assets.triggers[1])
					nG.translate(Assets.CONSTRAINTS[2].param * -1, Assets.CONSTRAINTS[3].param * -1);
				if(Assets.triggers[2])
					nG.rotate(Math.toRadians(Assets.CONSTRAINTS[0].param)*-1);
			}
			nG.drawImage(quads2[x], 0, 0, null);
		}
		Graphics2D bG = (Graphics2D)quad.getGraphics();
		bG.drawImage(quads2[0],0, 0,quad.getWidth()/2, quad.getHeight()/2,null);
		bG.drawImage(quads2[1],quad.getWidth()/2, 0,quad.getWidth()/2, quad.getHeight()/2,null);
		bG.drawImage(quads2[2],0, quad.getHeight()/2,quad.getWidth()/2, quad.getHeight()/2,null);
		bG.drawImage(quads2[3],quad.getWidth()/2, quad.getHeight()/2,quad.getWidth()/2, quad.getHeight()/2,null);
		
	}

}
