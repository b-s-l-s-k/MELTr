package com.bslsk.paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.bslsk.bin.GFrame;
import com.bslsk.gen.LifeThread;
import com.bslsk.info.Assets;

public class LifeMode extends PaintMode {

	public LifeThread lT;
	Thread thread;
	BufferedImage lifeB;
	Graphics2D lG;
	public LifeMode(int w, int h, int r)
	{
		lifeB = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
		lG = (Graphics2D)lifeB.getGraphics();
		lG.setBackground(new Color(0,0,0,0));
		//lG.setBackground(Color.TRANSLUCENT);
		lT =new LifeThread(w,h,r);
		thread = new Thread(lT);
		
		
		thread.start();
	}
	@Override
	public void paintTo(GFrame g) 
	{
		lG.clearRect(0, 0, Assets.WIDTH, Assets.HEIGHT);
		g.buffer.setColor(g.current);
		lG.setColor(Color.BLACK);
		for(int x = 0; x < Assets.WIDTH/10; x ++)
			for(int y = 0; y < Assets.HEIGHT/10; y++)
			{
				if(lT.grid[x][y] == true)
					lG.fillRect(x*10, y*10, 10, 10);//buffer.fillRect(x*10, y*10, 10, 10);
				else
					lG.clearRect(x*10, y*10, 10, 10);//buffer.fillRect(x*10, y*10, 10, 10);
			}
		g.buffer.drawImage(lifeB, 0, 0, Assets.WIDTH, Assets.HEIGHT, null);
	}

}
