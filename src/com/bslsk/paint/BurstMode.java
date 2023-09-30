package com.bslsk.paint;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.bslsk.bin.GFrame;
import com.bslsk.info.Assets;

public class BurstMode extends PaintMode {

	@Override
	public void paintTo(GFrame g) 
	{
		//int z = Assets.R.nextInt(20);
		for(int x = 0; x < (Assets.CONSTRAINTS[0].param /5) ; x++)
		{
			int mx = Assets.R.nextInt(Assets.WIDTH-200)+100;
			int sizeX = Assets.R.nextInt(200)+100;
			if(mx+sizeX > Assets.WIDTH)
			{
				int c = (mx + sizeX) -Assets.WIDTH;
				sizeX -= c;
			}
			int my = Assets.R.nextInt(Assets.HEIGHT-100)+50;
			int sizeY = Assets.R.nextInt(200)+100;
			if(my+sizeY > Assets.HEIGHT)
			{
				int c = (my + sizeY) -Assets.HEIGHT;
				sizeY -= c;
			}
			BufferedImage n = g.iB.getSubimage(mx, my,sizeX,sizeY);
			//BufferedImage n2 = g.iB.getSubimage(mx2, my2,sizeX,sizeY);
			g.buffer.drawImage(n, Assets.R.nextInt(Assets.WIDTH), Assets.R.nextInt(Assets.HEIGHT), null);
			//g.buffer.drawImage(n2, mx, my, null);
			//Graphics2D nG = (Graphics2D)n.getGraphics();
		}
		

	}


}
