package com.bslsk.gen;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class FilterEffect extends Effect {

	public static final int TYPE_BLUR = 0, TYPE_SHARPEN = 1, TYPE_EDGE = 2, TYPE_GRAIN = 3;
	public boolean active;
	public int type;
	public float strength;
	int iv, counter;
	public FilterEffect(int t, float str)
	{
		type = t;
		strength = str;
		iv = 3;//   1/3 speed to save on CPU usage
		counter= 0;
		active = false;
	}
	public void blend()
	{

	}
	@Override
	public void doEffect(Graphics2D g, BufferedImage i)
	{
		//if(active && counter == iv)
		//{
			float[] elements = new float[9];
			if(type == TYPE_BLUR)
			{
				elements = blur(elements);
			}
			else if(type == TYPE_SHARPEN)
			{
				elements = sharpen(elements);
			}
			else if(type == TYPE_EDGE)  
			{
				elements = edge(elements);
			}
			else if(type == TYPE_GRAIN)
			{
				elements = grain(elements);
			}
			/*
			else if(type == TYPE_BRIGHTEN)  
			{
				elements = brighten(elements);
			}
			else if(type == TYPE_NEG )
			{
				elements = negatives(elements);
			}
			*/

			convolve(g,i, elements);
			counter = 0;
		//}
		//else if(isEnabled() && counter < iv)
			//counter++;
			//System.out.println("TYPE " +type +" NOT ENABLED");
	}
	
	private void convolve(Graphics2D g, BufferedImage i, float[] elements)
	{
		Kernel k = new Kernel(3,3,elements);
		ConvolveOp co = new ConvolveOp(k);
		filter(g,i,co);
		
	}
	private void filter(Graphics2D g, BufferedImage i, BufferedImageOp co) 
	{
		BufferedImage n = new BufferedImage(i.getWidth(),i.getHeight(),i.getType());
		co.filter(i, n);
		g.drawImage(n, 0, 0, null);
		
	}
	private float[] negatives(float[] elements) 
	{
		return elements;

		
	}
	private float[] brighten(float[] elements) 
	{

		return elements;
		
	}
	private float[] edge(float[] elements) 
	{
		elements = 
				new float[] { 0.0f, -1.0f, 0.0f,
							-1.0f, 4.0f, -1.0f,
							0.0f, -1.0f, 0.0f};
			return elements;

		
	}
	private float[] sharpen(float[] elements) 
	{
		elements = 
			new float[] { 0.0f, -1.0f, 0.0f,
						-1.0f, 5.0f, -1.0f,
						0.0f, -1.0f, 0.0f};
		return elements;
		
	}
	private float[] blur(float[] elements) 
	{
		for(int x = 0; x < elements.length; x++)
			elements[x] = (((strength) * .15f) / 127.0f) + 0f;
		return elements;
	}
	private float[] grain(float[] elements)
	{
		float mult = (((strength) * 5.0f) / 127.0f);
		elements =
				new float[] { 0.0f, -mult, 0.0f,
							mult, 1.0f, -mult,
							0.0f, mult, 0.0f};
		return elements;
	}
	@Override
	public void toggle() {
		active = !active;
		System.out.println("Filter " + type + (active ? " ACTIVE":" DISABLED"));
	}

	@Override
	public boolean isEnabled() {
		return active;
	}
	@Override
	public void alter(int setting, int value) {
		// TODO Auto-generated method stub
		
	}

}
