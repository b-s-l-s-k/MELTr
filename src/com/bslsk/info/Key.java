package com.bslsk.info;

import com.bslsk.bin.GFrame;

public class Key 
{
	int key; // The code for the key == keyEvent.getKeyCode()
	Action action; // what the key is mapped to

	/**
	 * Creates a new Key
	 * @param k the KeyCode value of the key this represents
	 * @param a the Action that is performed when the Key is pressed
	 */
	public Key(int k, Action a)
	{
		key = k;

		action = a;
	}

	/**
	 * Perform the funtion assigned by this Key's Action
	 *
	 */
	public void pressed()
	{
		action.act();
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

	/**
	 * Gets the keycode value of this key
	 * @return int the keycode value
	 */
	public int getKey() {
		return key;
	}
}
