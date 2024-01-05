package com.bslsk.info;

import com.bslsk.bin.GFrame;

public class MultiKey extends Key 
{
	int key;
	Action[] actions;
	public MultiKey(int k, Action[] as)
	{
		super(k,as[0]);
		key = k;
		actions = as;
	}
	@Override
	public void pressed(GFrame g)
	{
		for(Action a: actions)
			a.act();
	}
	public String toString()
	{
		String s = "";
		for(Action a: actions)
			s += a.toString() + "   ";
		return key + "   " + action.toString();
	}
}
