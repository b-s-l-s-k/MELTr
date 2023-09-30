package com.bslsk.gen;

public class Shifter 
{

	public final static int TYPE_ANGLE = 0;
	public static final int TYPE_SCALE = 1;
	public static final int TYPE_TRAN_X = 2;
	public static final int TYPE_TRAN_Y = 3;
	public final static int I_FORWARD = 0, I_BACK = 1, I_PINGPONG = 2;
	 public int type;
	private int interval;
		private int c; //0 for forward, 1 for back
	private double rate;
	private double min, max;
	
	public boolean active;
	public Shifter(int t, int i, double r, double low, double high) 
	{
		type = t;
		interval = i;
		rate = r;
		min =low;
		max = high;
		c = 0;
		
	}
	public void toggleActive()
	{
		active = !active;
	}
	public double shift(double v)
	{
		if(interval == Shifter.I_PINGPONG)
		{
			if(c==0)
				v+=rate;
			else
				v-=rate;
			
			if(v >= max)
			{
				c=1;
				//System.out.println("PING");
			}
			else if(v <= min)
			{
				c=0;
				//System.out.println("PONG");
			}
		}
		else if(interval == Shifter.I_FORWARD)
		{
			v+=rate;
			
			if(v >= max)
				v=min;
		}
		else if(interval == Shifter.I_BACK)
		{
			v-=rate;
			
			if(v <= min)
				v=max;
		}
		
		return v;
	}
	public boolean isActive() {
		return active;
	}

}
