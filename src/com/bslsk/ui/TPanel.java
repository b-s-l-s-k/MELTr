package com.bslsk.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class TPanel extends JPanel 
{
	Graphics2D buffer;
	BufferedImage iB;
	public TPanel()
	{
		super();
		
	}
	public void addGraphics(Graphics2D b, BufferedImage i)
	{
		//super();
		buffer = b;
		iB = i;
	}
	@Override
	public void paintComponent(Graphics g)
	{
		g.drawImage(iB, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}
