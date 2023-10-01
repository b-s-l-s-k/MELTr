package com.bslsk.info;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

import com.bslsk.gen.Effect;
import com.bslsk.gen.GlitchEffect;
import com.bslsk.gen.Shifter;
import com.bslsk.paint.*;

public class Assets 
{
	public static Constraint[] CONSTRAINTS;
	public static final int ADD_CONTEXT = 0, REMOVE_CONTEXT = 1, SET_SETTING = 2, SET_DRAW = 3;
	public static final int CONTEXT_LINE = 0,  CONTEXT_CLEAR = 1,  CONTEXT_COLOR = 2,  CONTEXT_GLITCH = 3;
	public static final int SETTING_ANGLE = 0, SETTING_SCALE = 1, SETTING_TRANX = 2, SETTING_TRANY = 3, SETTING_DRAWTOGGLE = 4;
	public static final int DRAW_NORMAL = 0, DRAW_DOUBLE = 1, DRAW_2XDOUBLE = 2, DRAW_QUAD = 3, DRAW_GLITCH = 4, DRAW_LIFE = 5;
	public static final int SHIFT_UP = 0, SHIFT_DOWN = 1      ,      SETTING_NA = -1;
	
	
	public static int WIDTH, HEIGHT;
	public static double RATIO;
	public static boolean[] ANIM;
	public static Random R;
	
	public static ArrayList<Shifter> shifts;
	public static ArrayList<Effect> effects;
	
	public static Font drawFont;
	public static Color current;
	public static PaintMode[] getDefaultPaintModes()
	{
		return new PaintMode[] 
				{
						new NormalMode(),
						new ReflectMode(),
						new ReflectDoubleMode(),
						new QuadMode(),
						new GlitchMode(),
						new LifeMode(WIDTH,HEIGHT,250),
						new BurstMode(),
						new DistortMode()
				};
	}
	public static PaintMode[] getDefaultPaintModes(int w, int h, int r)
	{
		return new PaintMode[] 
				{
						new NormalMode(),
						new ReflectMode(),
						new ReflectDoubleMode(),
						new QuadMode(),
						new GlitchMode(),
						new LifeMode(w,h,r),
						new BurstMode(),
						new DistortMode()
				};
	}
	public static void setGlobalConstants(int w, int h, double r)
	{
		WIDTH = w;
		HEIGHT = h;
		RATIO = r;
		//REPLACE triggers[] in GFrame with ANIM[] 
		ANIM= new boolean[3];
		current = Color.black;
		R = new Random();
		
		shifts = new ArrayList<Shifter>();
		shifts.add(new Shifter(Shifter.TYPE_ANGLE, Shifter.I_PINGPONG,0.02,-70,70));
		shifts.add(new Shifter(Shifter.TYPE_SCALE, Shifter.I_PINGPONG,0.0002,0.01,1.1));
		shifts.add(new Shifter(Shifter.TYPE_TRAN_X, Shifter.I_PINGPONG,0.0002,-5,5));
		shifts.add(new Shifter(Shifter.TYPE_TRAN_Y, Shifter.I_PINGPONG,0.0002,-5,5));
		
		effects = new ArrayList<Effect>();
		effects.add(new GlitchEffect(GlitchEffect.O_HORIZONTAL,1,10,100));
		
		drawFont = new Font(Font.SANS_SERIF, Font.BOLD,100);
		System.out.println(w + "     " + h +"     " + r + "");
	}
	public static Constraint[] getDefaultContraints()
	{
		Assets.CONSTRAINTS  = new Constraint[] {
			new Constraint(Action.SETTING_ANGLE, 0,new int[] {-45,45}) 	,
			new Constraint(Action.SETTING_SCALE, 1,new int[] {0,2}) ,
			new Constraint(Action.SETTING_TRANX, -1, new int[] {-5,5}),
			new Constraint(Action.SETTING_TRANY, -1, new int[] {-5,5})
		};
		return Assets.CONSTRAINTS;
	}
	public static Constraint getConstraintBy(int type)
	{
		return CONSTRAINTS[type];
	}
	public static String export()
	{
		String total = CONSTRAINTS.length+"\n";
		for(Constraint c : CONSTRAINTS)
			total += c.toString() + "\n";
		
		total +=  "GLOBAL "+ WIDTH + " " + HEIGHT + " " + RATIO + "\n";
		return total;
				
	}
	public static void toggleAnim(int x)
	{
		ANIM[x] = !ANIM[x];
	}

}
