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
	public void released(GFrame g) 
	{
		if(action.getType()[0] == Action.KEY_DOWN)
			action.act(g);
		
	}
	public int getKey() {
		return key;
	}
}
