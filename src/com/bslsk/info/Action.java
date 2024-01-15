package com.bslsk.info;

import com.bslsk.bin.GFrame;
import com.bslsk.gen.CircleContext;
import com.bslsk.gen.ClearContext;
import com.bslsk.gen.ColorContext;
import com.bslsk.gen.ImageEffect;
import com.bslsk.gen.LifeContext;
import com.bslsk.gen.LineContext;
import com.bslsk.gen.Shifter;
import com.bslsk.gen.TrailContext;
import com.bslsk.gen.WordContext;

public class Action 
{
	//-----------------------TYPE---------------------------
	public static final int ADD_CONTEXT = 0, REMOVE_CONTEXT = 1, SET_SETTING = 2, SET_DRAW = 3, SET_EFFECT = 4, SET_SHIFTER = 5, KEY_DOWN = 6, MOVE_IMG = 7, COLOR_CHG = 8, RECORD = 9;
	
	//----------------------S-TYPE--------------------------
	//----------------------CONTEXT-------------------------
	public static final int CONTEXT_LINE = 0,  CONTEXT_CLEAR = 1,  CONTEXT_COLOR = 2,  CONTEXT_GLITCH = 3, CONTEXT_WORD = 4, CONTEXT_IMAGE = 5, CONTEXT_CIRCLE = 6;
	//----------------------SETTING---------------------------
	public static final int SETTING_ANGLE = 0, SETTING_SCALE = 1, SETTING_TRANX = 2, SETTING_TRANY = 3, SETTING_DRAWTOGGLE = 4;
	public static final int CHANGE_ANGLE = -1, CHANGE_SCALE = -2, CHANGE_TRANX = -3, CHANGE_TRANY = -4,CHANGE_DRAWTOGGLE = -5;
	public static final int IMG_X = 0, IMG_Y = 1, IMG_SCALEX = 2, IMG_SCALEY = 3, IMG_ROTATION = 4;
	//----------------------DRAW-MODE-------------------------
	public static final int DRAW_NORMAL = 0, DRAW_DOUBLE = 1, DRAW_2XDOUBLE = 2, DRAW_QUAD = 3, DRAW_GLITCH = 4, DRAW_LIFE = 5, DRAW_BURST = 6, DRAW_DIST = 7, DRAW_CENTER = 8;
	//----------------------SHIFTER---------------------------
	public static final int TOGGLE_ANGLE = 0, TOGGLE_SCALE = 1, TOGGLE_TRANX = 2, TOGGLE_TRANY = 3;
	public static final int C0 = 0, C1 = 1, C2 = 2;
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
	 * Perform a function according to the type, sType, and dType of the current Action, onto the running variables of the GFrame
	 *
	 */
	public void act()
	{
		if( type == ADD_CONTEXT)
		{
			if(sType == CONTEXT_LINE)
			{
				Assets.render.add(new LineContext(5,Assets.WIDTH,Assets.HEIGHT));
				
				return;
			}
			else if(sType == CONTEXT_CLEAR)
			{
				Assets.render.add(new ClearContext(Assets.WIDTH,Assets.HEIGHT));
				
				return;
			}
			else if(sType == CONTEXT_COLOR)
			{
				Assets.render.add(new ColorContext(3,Assets.WIDTH,Assets.HEIGHT));
				
				return;
			}
			else if(sType == CONTEXT_GLITCH)
			{
				Assets.render.add(TrailContext.randomContext(Assets.WIDTH, Assets.HEIGHT));
				
				return;
			}
			else if(sType == CONTEXT_WORD)
			{
				Assets.render.add(new WordContext("res/words.txt"));
				
				return;
			}
			else if(sType == CONTEXT_IMAGE)
			{
				//Assets.render.add(new WordContext("words.txt"));
				
				return;
			}
			else if(sType == CONTEXT_CIRCLE)
			{
				Assets.render.add(new CircleContext(5,100));
				
				return;
			}
		}
		else if( type == REMOVE_CONTEXT)
		{
			if(!Assets.render.isEmpty())
			{
				Assets.render.removeLast();
				return;
			}
		}
		else if( type == SET_SETTING)
		{
			
			if(sType == SETTING_ANGLE)
			{
				System.out.print("Angle:  ");
				if(dType == SHIFT_UP)
				{
					Assets.CONSTRAINTS[0].param += 1;
				}
				else if(dType == SHIFT_DOWN)
				{
					Assets.CONSTRAINTS[0].param -= 1;
				}
				System.out.println(Assets.CONSTRAINTS[0].param);
			}
			else if(sType == SETTING_SCALE)
			{
				System.out.print("SCALE:  ");
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

			Assets.painter.setActive((byte) (sType));
		}
		else if( type == SET_EFFECT)
		{//public static final int DRAW_NORMAL = 0, DRAW_DOUBLE = 1, DRAW_2XDOUBLE = 2, DRAW_QUAD = 3, DRAW_GLITCH = 4;
			if(Assets.CTRL)
				Assets.effects.get(sType).alter(0,1);
			else
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
			Assets.CTRL = true;
			System.out.println(Assets.CTRL + " = CTRL" );
		}
		else if( type == MOVE_IMG)
		{
			if(sType == IMG_X)
			{
				ImageEffect ii = (ImageEffect)Assets.effects.get(1);
				if(dType == SHIFT_UP)
					ii.x++;
				else if(dType == SHIFT_DOWN)
					ii.x--;
			}
			else if(sType == IMG_Y)
			{
				ImageEffect ii = (ImageEffect)Assets.effects.get(1);
				if(dType == SHIFT_UP)
					ii.y++;
				else if(dType == SHIFT_DOWN)
					ii.y--;
			}
			else if(sType == IMG_SCALEX)
			{
				ImageEffect ii = (ImageEffect)Assets.effects.get(1);
				if(dType == SHIFT_UP)
					ii.xScale+= 0.1;
				else if(dType == SHIFT_DOWN)
					ii.xScale-= 0.1;
			}
			else if(sType == IMG_SCALEY)
			{
				ImageEffect ii = (ImageEffect)Assets.effects.get(1);
				if(dType == SHIFT_UP)
					ii.yScale++;
				else if(dType == SHIFT_DOWN)
					ii.yScale--;
			}
			else if(sType == IMG_ROTATION)
			{
				ImageEffect ii = (ImageEffect)Assets.effects.get(1);
				if(dType == SHIFT_UP)
					ii.rotation++;
				else if(dType == SHIFT_DOWN)
					ii.rotation--;
			}
		}
		else if( type == COLOR_CHG)
		{
			Assets.current = Assets.COLOR[sType];
		}
		else if( type == RECORD)
		{
			if(sType == 0)
				Assets.recorders[dType].toggleRec();
			if(sType == 1)
				Assets.recorders[dType].toggleEvent();
		}
		
	}
	public void unAct(GFrame g)
	{
		if( type == KEY_DOWN)
		{
			Assets.CTRL = false;
			System.out.println(Assets.CTRL + " = CTRL" );
		}
	}
	public String toString()
	{
		return type + " " + sType + " " + dType + " ";
	}
	//----------OPTIONS----------------

	/**
	 *
	 * @return int[] representing type, sType, and dType
	 */
	public int[] getType() {
		return new int[] {type, sType, dType};
	}
}
