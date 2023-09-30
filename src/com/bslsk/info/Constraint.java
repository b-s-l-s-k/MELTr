package com.bslsk.info;

public class Constraint 
{
	public int type;
	public double param;
	public int[] bounds;
	public Constraint(int t, int p, int[] b)
	{
		type = t;
		param = p;
		bounds= b;
	}
	private boolean inBounds(int p)
	{
		if(p <= bounds[1] && p >= bounds[0])
			return true;
		return false;
	}
	public void setParam(int p)
	{
		if(inBounds(p))
			param = p;
	}
	public double getParam()
	{
		
		return param;
	}
	public double getParamAsDouble()
	{
		
		return 1.0 + ((double)param)/(10);
	}
	public void setBounds(int lower, int upper)
	{
		bounds[0] = lower;
		bounds[1] =  upper;
	}
	public void setBounds(int[] b)
	{
		bounds = b;
	}
	public int[] getBounds()
	{
		return bounds;
	}
	public int getLowerBound()
	{
		return bounds[0];
		
	}
	public int getUperBound()
	{
		return bounds[1];
	}
	public String toString()
	{
		return type + " " + param + " " + bounds[0] + " " + bounds[1];
	}
}
