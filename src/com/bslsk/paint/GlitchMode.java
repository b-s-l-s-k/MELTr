package com.bslsk.paint;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.bslsk.bin.GFrame;

public class GlitchMode extends PaintMode {

	@Override
	public void paintTo(GFrame g) 
	{
		BufferedImage n = g.iB.getSubimage(0, g.height/2,g.width/2,g.height/2);
		Graphics2D nG = (Graphics2D)n.getGraphics();
		double tscale = g.scale;
		if(g.scale > 1.0)
			tscale = g.scale-1.0;
		nG.scale(tscale, tscale);
		nG.translate(tscale*g.width, tscale*g.height);
		nG.rotate(Math.toRadians(g.angle));
		
		nG.drawImage(n, (int)(tscale*g.width), (int)(tscale*g.height), g.width/2, g.height/2, null);
		g.buffer.drawImage(n, 0,g.height/2, g.width/2, g.height, null);

	}

}
