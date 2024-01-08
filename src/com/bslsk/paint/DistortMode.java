package com.bslsk.paint;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.bslsk.bin.GFrame;
import com.bslsk.gen.*;
import com.bslsk.info.Assets;

public class DistortMode extends PaintMode {

	@Override
	public void paintTo(GFrame g) 
	{
		for(int x = 0; x < g.getWidth()/2; x += g.width/(int)(g.ratio*4))
			for(int y = 0; y < g.getHeight();y+=g.height/(int)(g.ratio*4))
			{
				BufferedImage n = g.iB.getSubimage(x, y,g.width/(int)(g.ratio*4), g.height/(int)(g.ratio*4));
				Graphics2D nG = (Graphics2D)n.getGraphics();
				
				//if(Assets.triggers[1])
					//nG.translate(Assets.CONSTRAINTS[2].param, Assets.CONSTRAINTS[3].param);
				//innerPaint(n);
				double s = 100.0/(Assets.CONSTRAINTS[1].param*100.0)/(double)(Assets.CONSTRAINTS[1].bounds[1]);
				//System.out.println(s+"");
				if(s>=1)
					s=0.999;
				double sw = (n.getWidth() - (n.getWidth()*s))/2.0;
				double sh = (n.getHeight() - (n.getHeight()*s))/2.0;
				//System.out.println(sw + "  "+ sh + " S:" + s);
				BufferedImage nx = n.getSubimage((int)(sw), (int)(sh), (int)(n.getWidth()*s),  (int)(n.getWidth()*s));
				Graphics2D nxg = (Graphics2D)nx.getGraphics();
				nxg.rotate(-2 * Math.toRadians(Assets.CONSTRAINTS[0].param));
				nxg.drawImage(nx,0,0,nx.getWidth(),nx.getHeight(),null);
				nG.drawImage(nx, (int)sw, (int)sh, (int)(n.getWidth()*s),  (int)(n.getWidth()*s), null);
				if(Assets.triggers[0])
					nG.scale(Assets.CONSTRAINTS[1].param,Assets.CONSTRAINTS[1].param);
				if(Assets.triggers[2])
					nG.rotate(Math.toRadians(Assets.CONSTRAINTS[0].param));
				
				nG.drawImage(n, 0, 0, g.width/(int)(g.ratio*4), g.height/4, null);
				if(Assets.triggers[1])
					g.buffer.drawImage(n, x-(int)Assets.CONSTRAINTS[2].param, y-(int)Assets.CONSTRAINTS[3].param, g.width/(int)(g.ratio*4), g.height/4, null);
				else
					g.buffer.drawImage(n, x, y, g.width/(int)(g.ratio*4), g.height/4, null);
			}

		g.buffer.drawImage(g.iB, g.width, 0, g.width/2, g.height, 0, 0, g.width/2, g.height, null);
		
	}
	private void innerPaint(BufferedImage i)
	{
		
		double s = 100.0/(Assets.CONSTRAINTS[1].param*100.0)/(double)(Assets.CONSTRAINTS[1].bounds[1]);
		if(s>=1)
			s=0.999;
		double sw = (i.getWidth() - (i.getWidth()*s))/2.0;
		double sh = (i.getHeight() - (i.getHeight()*s))/2.0;
		System.out.println(sw + "  "+ sh + " S:" + s);
		BufferedImage nx = i.getSubimage((int)(sw), (int)(sh), (int)(i.getWidth()*s),  (int)(i.getWidth()*s));
		Graphics2D nxg = (Graphics2D)nx.getGraphics();
		nxg.rotate(-1 * Math.toRadians(Assets.CONSTRAINTS[0].param));
		((Graphics2D)i.getGraphics()).drawImage(nx, (int)sw, (int)sh, (int)(i.getWidth()*s),  (int)(i.getWidth()*s), null);
		
		
	}
}
