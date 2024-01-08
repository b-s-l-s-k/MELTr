package com.bslsk.paint;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.bslsk.bin.GFrame;
import com.bslsk.info.Assets;

public class ReflectDoubleMode extends PaintMode {

	@Override
	public void paintTo(GFrame g)
	{
		for(int x = 0; x < g.width/2; x += g.width/(g.ratio*8))
			for(int y = 0; y < g.height;y+=(g.height/(g.ratio*8)))
			{
				if(y + (g.height/4) >= g.height)
					break;

				//System.out.println(y + "     " + g.height);
				BufferedImage n = g.iB.getSubimage(x, y,g.width/(int)(g.ratio*8), (int)(g.ratio*8));
				Graphics2D nG = (Graphics2D)n.getGraphics();
				//if(triggers[0])
					//nG.scale(scale, scale);
				//if(triggers[1])
					//nG.translate(tranX, tranY);
				if(Assets.triggers[2])
					nG.rotate(Math.toRadians(Assets.CONSTRAINTS[0].param));
				
				
				nG.drawImage(n, 0, 0, Assets.WIDTH/(int)(Assets.RATIO*8), Assets.HEIGHT/8, null);
				if(Assets.triggers[1])
					g.buffer.drawImage(n, x+(int)Assets.CONSTRAINTS[2].param, y+(int)Assets.CONSTRAINTS[3].param, (int)((Assets.WIDTH/(int)(Assets.RATIO*8))*Assets.CONSTRAINTS[1].param), (int)((Assets.HEIGHT/8) * Assets.CONSTRAINTS[1].param), null);
				else
					g.buffer.drawImage(n, x, y, Assets.WIDTH/(int)(Assets.RATIO*8), Assets.HEIGHT/8, null);
			}
		g.buffer.drawImage(g.iB, Assets.WIDTH, 0, Assets.WIDTH/2,Assets.HEIGHT, 0, 0, Assets.WIDTH/2, Assets.HEIGHT, null);

	}

}
