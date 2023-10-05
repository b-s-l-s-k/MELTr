package com.bslsk.gen;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GlitchEffect extends Effect {

	public static final int O_HORIZONTAL = 0, O_VERTICAL = 1, O_BOTH = 1;
	private int type, maxSize, minSize;
	int intensity;
	Random r1;
	boolean enabled = false;
	public GlitchEffect(int t, int i, int min, int max)
	{
		type = t;
		minSize = min;
		maxSize = max;
		intensity = i;
		
		r1 = new Random();
	}
	@Override
	public void doEffect(Graphics2D g, BufferedImage i) 
	{
		if(type == O_HORIZONTAL || type == O_BOTH)
		{
			for(int x = 0; x < intensity; x++)
			{
				BufferedImage b = i.getSubimage(0, r1.nextInt(i.getHeight()-maxSize),  i.getWidth(), r1.nextInt(maxSize-minSize)+minSize);
				g.drawImage(b,r1.nextInt(i.getWidth()/4), r1.nextInt(i.getHeight()),null);
			}
		}
		if(type == O_VERTICAL || type == O_BOTH)
		{
			
		}

	}
	@Override
	public void toggle(){enabled = !enabled;}
	@Override
	public boolean isEnabled() {return enabled;}
	@Override
	public void alter(int setting, int value) {
		// TODO Auto-generated method stub
		
	}

}
