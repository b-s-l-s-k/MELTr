package com.bslsk.info;

import com.bslsk.bin.GFrame;
import com.bslsk.gen.CircleContext;
import com.bslsk.gen.ClearContext;
import com.bslsk.gen.ColorContext;
import com.bslsk.gen.LifeContext;
import com.bslsk.gen.LineContext;
import com.bslsk.gen.Shifter;
import com.bslsk.gen.TrailContext;
import com.bslsk.gen.WordContext;

public class Action 
{
	//-----------------------TYPE---------------------------
	public static final int ADD_CONTEXT = 0, REMOVE_CONTEXT = 1, SET_SETTING = 2, SET_DRAW = 3, SET_EFFECT = 4, SET_SHIFTER = 5, KEY_DOWN = 6;
	
	//----------------------S-TYPE--------------------------
	//----------------------CONTEXT-------------------------
	public static final int CONTEXT_LINE = 0,  CONTEXT_CLEAR = 1,  CONTEXT_COLOR = 2,  CONTEXT_GLITCH = 3, CONTEXT_WORD = 4, CONTEXT_IMAGE = 5, CONTEXT_CIRCLE = 6;
	//----------------------SETTING---------------------------
	public static final int SETTING_ANGLE = 0, SETTING_SCALE = 1, SETTING_TRANX = 2, SETTING_TRANY = 3, SETTING_DRAWTOGGLE = 4;
	public static final int CHANGE_ANGLE = -1, CHANGE_SCALE = -2, CHANGE_TRANX = -3, CHANGE_TRANY = -4,CHANGE_DRAWTOGGLE = -5;
	//----------------------DRAW-MODE-------------------------
	public static final int DRAW_NORMAL = 0, DRAW_DOUBLE = 1, DRAW_2XDOUBLE = 2, DRAW_QUAD = 3, DRAW_GLITCH = 4, DRAW_LIFE = 5, DRAW_BURST = 6, DRAW_DIST = 7;
	//----------------------SHIFTER---------------------------
	public static final int TOGGLE_ANGLE = 0, TOGGLE_SCALE = 1, TOGGLE_TRANX = 2, TOGGLE_TRANY = 3;
	//----------------------DTYPE---------------------------
	//----------------------SHIFT---------------------------
	public static final int SHIFT_UP = 0, SHIFT_DOWN = 1 ,SHIFT_SET = 2      ,      SETTING_NA = -1;
	private Object actor;
	private int type;
	private int sType;
	private int dType;
	//private int xType;
	
	public Action()
	{
		
	}
	//if d is -1 (SETTING_NA), it is not counted
	/**
	 * Create a new Action with the given type, subtype, and dtype
	 * @param t Type of Action
	 * @param s Sub-Type of Action
	 * @param d Direction/Secondary Type
	 */
	public Action(int t, int s, int d)
	{
		type = t;
		sType = s;
		dType = d;
	}
	/**
	 * Perform a function according to the types, onto the running variables of the GFrame 
	 * @param gf The GFrame to be manipulated by this Action
	 */
	public void act(GFrame gf)
	{
		if( type == ADD_CONTEXT)
		{
			if(sType == CONTEXT_LINE)
			{
				gf.render.add(new LineContext(5,gf.width,gf.height));
				
				return;
			}
			else if(sType == CONTEXT_CLEAR)
			{
				gf.render.add(new ClearContext(gf.width,gf.height));
				
				return;
			}
			else if(sType == CONTEXT_COLOR)
			{
				gf.render.add(new ColorContext(3,gf.width,gf.height));
				
				return;
			}
			else if(sType == CONTEXT_GLITCH)
			{
				gf.render.add(TrailContext.randomContext(gf.width, gf.height));
				
				return;
			}
			else if(sType == CONTEXT_WORD)
			{
				gf.render.add(new WordContext("words.txt"));
				
				return;
			}
			else if(sType == CONTEXT_IMAGE)
			{
				gf.render.add(new WordContext("words.txt"));
				
				return;
			}
			else if(sType == CONTEXT_CIRCLE)
			{
				gf.render.add(new CircleContext(5,100));
				
				return;
			}
		}
		else if( type == REMOVE_CONTEXT)
		{
			if(gf.render.size() > 0)
			{
				gf.render.remove(gf.render.size()-1);
				return;
			}
		}
		else if( type == SET_SETTING)
		{
			
			if(sType == SETTING_ANGLE)
			{
				System.out.println("Angle");
				if(dType == SHIFT_UP)
				{
					Assets.CONSTRAINTS[0].param += 1;
				}
				else if(dType == SHIFT_DOWN)
				{
					Assets.CONSTRAINTS[0].param -= 1;
				}
				else if(dType == SHIFT_DOWN)
				{
					Assets.CONSTRAINTS[0].param -= 1;
				}
			}
			else if(sType == SETTING_SCALE)
			{
				System.out.println("SCALE");
				if(dType == SHIFT_UP)
				{
					Assets.CONSTRAINTS[1].param += 0.1;
					System.out.println(Assets.CONSTRAINTS[1].param);
					//Assets.CONSTRAINTS[SETTING_SCALE].setParam(Assets.CONSTRAINTS[SETTING_SCALE].getParam() + 1);
				}
				else if(dType == SHIFT_DOWN)
				{
					Assets.CONSTRAINTS[1].param -= 0.1;
					System.out.println(Assets.CONSTRAINTS[1].param);
					//Assets.CONSTRAINTS[SETTING_SCALE].setParam(Assets.CONSTRAINTS[SETTING_SCALE].getParam() - 1);
				}
			}
			else if(sType == SETTING_TRANX)
			{
				if(dType == SHIFT_UP)
				{
					Assets.CONSTRAINTS[2].param += 1;
				}
				else if(dType == SHIFT_DOWN)
				{
					Assets.CONSTRAINTS[2].param -= 1;
				}
			}
			else if(sType == SETTING_TRANY)
			{
				if(dType == SHIFT_UP)
				{
					Assets.CONSTRAINTS[3].param += 1;
				}
				else if(dType == SHIFT_DOWN)
				{
					Assets.CONSTRAINTS[3].param -= 1;
				}
			}
			else if(sType == CHANGE_ANGLE)
			{
				Assets.CONSTRAINTS[0].setParam(dType);
				System.out.println("MULTI KEY: Hit angle" );
			}
			else if(sType == CHANGE_SCALE)
			{
				Assets.CONSTRAINTS[1].setParam(dType);
			}
			else if(sType == CHANGE_TRANX)
			{
				Assets.CONSTRAINTS[2].setParam(dType);
			}
			else if(sType == CHANGE_TRANY)
			{
				Assets.CONSTRAINTS[3].setParam(dType);
			}
		}
		else if( type == SET_DRAW)
		{//public static final int DRAW_NORMAL = 0, DRAW_DOUBLE = 1, DRAW_2XDOUBLE = 2, DRAW_QUAD = 3, DRAW_GLITCH = 4;
				
			gf.mode = (byte) (sType);
			gf.painter.setActive(gf.mode);
		}
		else if( type == SET_EFFECT)
		{//public static final int DRAW_NORMAL = 0, DRAW_DOUBLE = 1, DRAW_2XDOUBLE = 2, DRAW_QUAD = 3, DRAW_GLITCH = 4;
				
			Assets.effects.get(sType).toggle();
		}
		else if( type == SET_SHIFTER)
		{
			for(Shifter s : Assets.shifts)
				if(s.type == sType)
					s.toggleActive();
		}
		else if( type == KEY_DOWN)
		{
			Assets.CTRL = !Assets.CTRL;
			System.out.println(Assets.CTRL + " = CTRL" );
		}
		
		
	}
	public String toString()
	{
		return type + " " + sType + " " + dType + " ";
	}
	//----------OPTIONS----------------
	public int[] getType() {
		return new int[] {type, sType, dType};
	}
}
