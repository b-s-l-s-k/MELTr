package com.bslsk.info;

/**
 * Constraint is used to store the value of variable, and ensure that it cannot be changed to values outside
 */
public class Constraint 
{
	public int type;
	public double param;
	public int[] bounds;

	/**
	 * Creates a new Constraint based off input parameters
	 * @param t int type of constraint (mostly useless)
	 * @param p int param as the initial value to set
	 * @param b int[] the bounds of the param in the format {lower, upper}
	 */
	public Constraint(int t, int p, int[] b)
	{
		type = t;
		param = p;
		bounds= b;
	}

	/**
	 * checks if the given value of p is within the Constraints bounds
	 * @param p the value to check
	 * @return boolean, true if is in bounds, false otherwise
	 */
	private boolean inBounds(int p)
	{
		if(p <= bounds[1] && p >= bounds[0])
			return true;
		return false;
	}

	/**
	 * Sets the param of the Constraint to the given value
	 * @param p the param to set
	 */
	public void setParam(int p)
	{
		if(inBounds(p))
			param = p;
	}

	/**
	 * Gets the current param
	 * @return param
	 */
	public double getParam()
	{
		return param;
	}

	/**
	 * Set bounds to a slit lower/upper value
	 * @param lower the lower bound
	 * @param upper the upper bound
	 */
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
	public int getUpperBound()
	{
		return bounds[1];
	}
	public String toString()
	{
		return type + " " + param + " " + bounds[0] + " " + bounds[1];
	}
}
