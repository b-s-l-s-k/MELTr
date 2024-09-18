package com.bslsk.paint;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import com.bslsk.gen.ImageEffect;
import com.bslsk.info.*;
public class PaintBrush 
{
	public boolean drawingMode; 		// true allows the user to draw on the screen with mouse/touch
	public boolean mDown; 				// if the mouse/touch is currently down
	public boolean imgMode;				//if true, draw image instead of color
	public BufferedImage drawI; 		// draw Image
	public Graphics2D drawG; 			// graphics that draws to drawI
	public byte shape; 					//0 = Circle   1 = Square   2 = Polygon
	public Color dmC; 					//current Draw Mode Color
	public boolean[] colorchange; 		//[0] is red [1] is green [2] is blue

	public PaintBrush(int width, int height)
	{
		colorchange = new boolean[3];
		drawI = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
		drawG = (Graphics2D)drawI.getGraphics();
		drawG.setBackground(new Color(0,0,0,0));
	}
	/**
	 * Paints the current brush to the screen 
	 * @param e MouseEvent detailing the x/y locations of the current mouse press to be drawn
	 */
	public void paint(MouseEvent e)
	{
		if(drawingMode && mDown && !imgMode)
		{
			Color backup = new Color(dmC.getRed(),dmC.getGreen(),dmC.getBlue());
			if(colorchange[0])
			{
				try {
					if(Assets.R.nextBoolean())
						dmC = new Color(dmC.getRed()+3,dmC.getGreen(),dmC.getBlue());
					else
						dmC = new Color(dmC.getRed()-3,dmC.getGreen(),dmC.getBlue());}
				catch(Exception eee) {dmC = backup;}
			}
			if(colorchange[1])
			{
				try {
					if(Assets.R.nextBoolean())
						dmC = new Color(dmC.getRed(),dmC.getGreen()+1,dmC.getBlue());
					else
						dmC = new Color(dmC.getRed(),dmC.getGreen()-1,dmC.getBlue());}
				catch(Exception eee) {dmC = backup;}
			}
			if(colorchange[2])
			{
				try {
					if(Assets.R.nextBoolean())
						dmC = new Color(dmC.getRed(),dmC.getGreen(),dmC.getBlue()+1);
					else
						dmC = new Color(dmC.getRed(),dmC.getGreen(),dmC.getBlue()-1);}
				catch(Exception eee) {dmC = backup;}
			}

			drawG.setColor(dmC);
			drawG.fillOval(e.getX()-15, e.getY()-15, 30, 30);

		}
		if(drawingMode && mDown && imgMode)
		{
			ImageEffect ii = (ImageEffect)Assets.effects.get(1);
			BufferedImage i = ii.img[ii.active_img];
			drawG.drawImage(i, e.getX(),e.getY(),30,30,null);
		}
	}//Assets.effect.get(1)
	/**
	 * Gets the current instance of the brush's "drawI"
	 * @return current instance of drawI
	 */
	public BufferedImage getImage()
	{
		return drawI;
	}
	/**
	 * Sets the color-shifting mode for the brush
	 * @param s the color mode to be toggled (0r,1g,2b). \ns must be between 0-2, or else it will not function and simply return 
	 * 
	 */
	public void setColor(int s) 
	{
		if(s > 0 && s < colorchange.length)
			colorchange[s] = !colorchange[s];
		else return;

	}
	public void setBrushColor(Color c)
	{
		dmC = c;

	}
}
