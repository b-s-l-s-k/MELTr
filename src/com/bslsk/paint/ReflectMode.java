package com.bslsk.paint;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.bslsk.bin.GFrame;
import com.bslsk.gen.*;
import com.bslsk.info.Assets;

public class ReflectMode extends PaintMode
{

	@Override
	public void paintTo(GFrame g) 
	{
		for(int x = 0; x <= g.width/2; x += g.width/4)
			for(int y = 0; y < g.height;y+=(g.height/4))
			{
				//System.out.println("Y: " + y);

				BufferedImage n = g.iB.getSubimage(x, y,(g.width/4)-1, (g.height/4)-1);
				Graphics2D nG = (Graphics2D)n.getGraphics();
				double[] pr = new double[] {Assets.CONSTRAINTS[2].param , Assets.CONSTRAINTS[3].param };
				pr[0] *= n.getWidth();
				pr[1] *= n.getHeight();
				nG.scale(Assets.CONSTRAINTS[1].param,Assets.CONSTRAINTS[1].param);
				nG.translate(Assets.CONSTRAINTS[2].param, Assets.CONSTRAINTS[3].param);

				nG.rotate(Math.toRadians(Assets.CONSTRAINTS[0].param),(double)n.getWidth()/2,(double)n.getHeight()/2);

				
				
				nG.drawImage(n, 0, 0, g.width/4, g.height/4, null);

				//if(Assets.triggers[1])
				g.buffer.drawImage(n, x+(int)Assets.CONSTRAINTS[2].param, y+(int)Assets.CONSTRAINTS[3].param, g.width/4, g.height/4, null);
				//else
					//g.buffer.drawImage(n, x, y, g.width/(int)(g.ratio*4), g.height/4, null);
				//System.out.println(x +"," + y);
			}
		g.buffer.drawImage(g.iB, g.width, 0, g.width/2, g.height, 0, 0, g.width/2, g.height, null);

	}

}
