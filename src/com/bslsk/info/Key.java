package com.bslsk.info;

import com.bslsk.bin.GFrame;

public class Key 
{
	int key; // The code for the key == keyEvent.getKeyCode()
	Action action; // what the key is mapped to
	public Key(int k, Action a)
	{
		key = k;

		action = a;
	}
	public void pressed(GFrame g)
	{
		action.act(g);
	}
	public String toString()
	{
		return key + "   " + action.toString();
	}
}
