package com.bslsk.paint;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.bslsk.bin.GFrame;
import com.bslsk.info.Assets;

public class QuadMode extends PaintMode 
{

	@Override
	public void paintTo(GFrame g) 
	{
		//new ReflectMode().paintTo(g);
		g.buffer.drawImage(g.iB, 0,0, Assets.WIDTH, Assets.HEIGHT/2, 0, Assets.HEIGHT, Assets.WIDTH, Assets.HEIGHT/2,null);

	}

}
