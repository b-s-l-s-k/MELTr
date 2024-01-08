package com.bslsk.paint;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.bslsk.bin.GFrame;
import com.bslsk.info.Assets;

public class CenterMode extends PaintMode {

	@Override
	public void paintTo(GFrame g)
	{
		//Copy current screen
		BufferedImage nFrame = new BufferedImage(g.iB.getWidth(),g.iB.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D nG = (Graphics2D)nFrame.getGraphics();
		if(Assets.triggers[2])
			nG.rotate(Math.toRadians(Assets.CONSTRAINTS[0].param));
		nG.drawImage(g.iB, 0, 0, null);
		
		//Center new image in the middle of the screen
		int w = nFrame.getWidth()/5;
		int h = nFrame.getHeight()/5;
		int x = w*2;
		int y = h*2;
		if(Assets.triggers[2])
			nG.rotate(Math.toRadians(Assets.CONSTRAINTS[0].param));
		nG.drawImage(nFrame, 0, 0,null);
		g.buffer.drawImage(nFrame, x, y, w, h, null);
		g.buffer.drawImage(g.iB, 
				(int)((g.iB.getWidth() - (g.iB.getWidth()*Assets.CONSTRAINTS[1].param))/2),
				(int)((g.iB.getHeight() - (g.iB.getHeight()*Assets.CONSTRAINTS[1].param))/2),
				(int)(g.iB.getWidth()*Assets.CONSTRAINTS[1].param), (int)(g.iB.getHeight()*Assets.CONSTRAINTS[1].param),null);
	}

}
