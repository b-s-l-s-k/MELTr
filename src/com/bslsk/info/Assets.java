package com.bslsk.info;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.bslsk.gen.*;
import com.bslsk.paint.*;

public class Assets 
{
	public static final String VERSION_ID = "v0.2.17a";
	public static Constraint[] CONSTRAINTS;
	
	
	public static int WIDTH, HEIGHT;
	public static double RATIO;
	
	public static boolean CTRL;
	public static boolean SHIFT;
	public static boolean[] ANIM;
	public static Random R;
	
	public static ArrayList<Shifter> shifts;
	public static ArrayList<Effect> effects;
	
	public static Font drawFont;
	public static Color current;

	public static ArrayList<GContext> render;

	public static byte mode;

	public static Painter painter;

	public static boolean[] triggers;
	public static PaintMode[] getDefaultPaintModes()
	{
		return new PaintMode[] 
				{
						new NormalMode(),
						new ReflectMode(),
						new ReflectDoubleMode(),
						new QuadMode(),
						new GlitchMode(),
						//new LifeMode(WIDTH,HEIGHT,250),
						new MeltMode(),
						new BurstMode(),
						new DistortMode(),
						new CenterMode()
				};
	}
	public static void initRender()
	{
		render = new ArrayList<GContext>();
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
						//new LifeMode(w,h,r),
						new MeltMode(),
						new BurstMode(),
						new DistortMode(),
						new CenterMode()
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
		effects.add(new ImageEffect());
		
		drawFont = new Font(Font.SANS_SERIF, Font.BOLD,100);

		triggers = new boolean[] {false,false,false,false};
		System.out.println(w + "     " + h +"     " + r );
	}
	
	
	public static void loadPainter(String loc)
	{
		FileInputStream fis;
		ArrayList<PaintMode> modes = new ArrayList<PaintMode>();
		ArrayList<Integer> links = new ArrayList<Integer>();
		try {
			File f = new File(loc);
			f = f.getAbsoluteFile();
			fis = new FileInputStream(f);
			Scanner s1 = new Scanner(fis);
			while(s1.hasNext()) // LAYOUT: [keychar] [type] [sType] [dType] 
			{
				int next = s1.nextInt();
				modes.add(Assets.getDefaultPaintModes()[next]);
				links.add(Integer.valueOf(s1.nextInt()));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		int[] lll = new int[links.size()];
		for(int x = 0; x < lll.length;x ++)
			lll[x] = links.get(x).intValue();
		
		PaintMode[] nMode = new PaintMode[modes.size()];
		for(int x = 0; x < nMode.length;x ++)
			nMode[x] = modes.get(x);
		painter =  new Painter(nMode, lll);
	}
	
	
	
	public static void getDefaultContraints()
	{
		Assets.CONSTRAINTS  = new Constraint[] {
			new Constraint(Action.SETTING_ANGLE, 0,new int[] {-45,45}) 	,
			new Constraint(Action.SETTING_SCALE, 1,new int[] {0,2}) ,
			new Constraint(Action.SETTING_TRANX, -1, new int[] {-5,5}),
			new Constraint(Action.SETTING_TRANY, -1, new int[] {-5,5})
		};
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
	public static void toggleTriggers()
	{
		triggers = new boolean[] {!triggers[0],!triggers[1],!triggers[2],!triggers[3]};
	}
}
