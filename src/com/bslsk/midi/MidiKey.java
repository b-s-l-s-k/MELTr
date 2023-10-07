package com.bslsk.midi;

import com.bslsk.bin.GFrame;
import com.bslsk.info.Action;

public class MidiKey 
{
	int key;
	int keyType;
	Action action;
	public MidiKey(int k, Action a)
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
			action.unAct(g);
		
	}
	public int getKey() {
		return key;
	}
}
