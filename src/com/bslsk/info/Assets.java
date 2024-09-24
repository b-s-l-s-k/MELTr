package com.bslsk.info;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import com.bslsk.gen.*;
import com.bslsk.paint.*;
import com.bslsk.util.ColorStack;

import javax.imageio.ImageIO;

public class Assets 
{
	public static final String VERSION_ID = "v0.6.15a";
    public static final Color[] COLOR = {Color.red,Color.green,Color.blue,Color.cyan,Color.magenta,Color.orange};
	public static float DRAW_OPACITY = 1.0F;
	public static Constraint[] CONSTRAINTS;
	public static Recorder[] recorders;
	
	public static int WIDTH, HEIGHT;
	public static double RATIO;
	public static int SPEED;
	public static boolean CTRL;
	public static boolean SHIFT;
	public static boolean[] ANIM;
	public static Random R;
	
	public static ArrayList<Shifter> shifts;
	public static ArrayList<Effect> effects;
	
	public static Font drawFont;
	public static Color current;
//-------------------------------------------------------------------------------------------------
	public static ArrayList<GContext> render;
	public static GContext[] context;
	public static boolean[] cEnabled;
//-------------------------------------------------------------------------------------------------
	public static byte mode;

	public static Painter painter;

	public static boolean[] triggers;
	public static BufferedImage[] loadedIMG;

	public static ColorStack cStack;
	/**
	 * Returns a default selection of PaintModes
	 * @return PaintMode[] A generic collection of PaintModes
	 */
	public static PaintMode[] getDefaultPaintModes()
	{
		return new PaintMode[] 
				{
						new NormalMode(),
						new ReflectMode(),
						new InvertMode(),
						new QuadMode(),
						new GlitchMode(),
						//new LifeMode(WIDTH,HEIGHT,250),
						new MeltMode(),
						new BurstMode(),
						new DistortMode(),
						new CenterMode()
				};
	}

	/**
	 * [UNUSED/NONFUNCTIONING] Returns a custom selection of PaintModes
	 * @return PaintMode[] A custom collection of PaintModes
	 */
	public static PaintMode[] getCustomPaintModes()
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

	/**
	 * Loads a new GContext renderer
	 */
	public static void initRender()
	{
		render = new ArrayList<GContext>();
		context = new GContext[9];
		initContexts();
	}

	/**
	 * Sets the values for neccesary Assets that allows Meltr to function
	 * @param w Width of the rendering window
	 * @param h Height of the rendering window
	 * @param r Width:Height ratio
	 */
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
		loadImages();
		System.out.println(w + "     " + h +"     " + r );
		cStack = new ColorStack();

	}

	/**
	 * Loads the values of Links and Modes to the Painter via config from the specified file
	 * @param loc the location of the file to read Painter config from
	 */
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


	/**
	 * Initializes Constraints and Recorder
	 */
	public static void getDefaultContraints()
	{
		Assets.CONSTRAINTS  = new Constraint[] {
			new Constraint(Action.SETTING_ANGLE, 0,new int[] {-45,45}) 	,
			new Constraint(Action.SETTING_SCALE, 1,new int[] {0,2}) ,
			new Constraint(Action.SETTING_TRANX, -1, new int[] {-5,5}),
			new Constraint(Action.SETTING_TRANY, -1, new int[] {-5,5})
		};
		Assets.recorders = new Recorder[]{
				new Recorder(0),//angle
				new Recorder(1),//scale
				new Recorder(2),//tranx
				new Recorder(3),//trany
				new Recorder(4),//color rec
				new Recorder(5), //red
				new Recorder(6), //green
				new Recorder(7)//blue
		};
	}

	/**
	 *
	 * @param type int value representing the index of a specific constraint within Constraints[]
	 * @return Constraint of list of Constraints specified by type
	 */
	public static Constraint getConstraintBy(int type)
	{
		return CONSTRAINTS[type];
	}

	/**
	 * Gets a String representation of the Constraints
	 * @return String of concatenated Constraint values (For Debug/Logging)
	 */
	public static String export()
	{
		String total = CONSTRAINTS.length+"\n";
		for(Constraint c : CONSTRAINTS)
			total += c.toString() + "\n";
		
		total +=  "GLOBAL "+ WIDTH + " " + HEIGHT + " " + RATIO + "\n";
		return total;
				
	}

	/**
	 * [UNUSED/NONFUNCTIONAL]
	 * @param x the index of the animation to toggle
	 */
	public static void toggleAnim(int x)
	{
		ANIM[x] = !ANIM[x];
	}

	/**
	 * Flips the values of all boolean values within "triggers"
	 */
	public static void toggleTriggers()
	{
		triggers = new boolean[] {!triggers[0],!triggers[1],!triggers[2],!triggers[3]};
	}

	/**
	 * [NOT FUNCTIONING PROPERLY]Load images from the /res/img/ folder
	 */
	private static void loadImages()
	{
		File folder = new File("res/img/");
		File[] list = folder.listFiles();
		loadedIMG = new BufferedImage[list.length];
		for(int x = 0; x < list.length;x++)
		{
			FileInputStream fis;
			try
			{
				System.out.println(list[x].getAbsolutePath());
				//loadedIMG[x] = (BufferedImage)(Image)Toolkit.getDefaultToolkit().createImage(list[x].getAbsolutePath());
				//new BufferedImage()
				loadedIMG[x] = ImageIO.read(list[x]);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public static void adjustMidi(int type, int value)
	{
		int[] bounds = CONSTRAINTS[type].getBounds();
		int range = bounds[1] - bounds[0];
		double z = (((double)(range * value))/127.0);
		z += bounds[0];
		CONSTRAINTS[type].param = z;
	}

	public static void adjustMidiContext(int type, int value)
	{
		if(type == 14)
			context[0].modify(value);
		else if(type == 15)
			context[1].modify(value);
		else if(type == 16)
			context[2].modify(value);
		else if(type == 22)
			DRAW_OPACITY = calcOpacity(value);
	}

	private static float calcOpacity(int value)
	{
		//x of 127 == z of 100
		return (value * 100.0f) / 12700.0f ;
	}

	public static void initContexts()
	{
		cEnabled = new boolean[9];
		context[0] = new ColorContext(3,Assets.WIDTH,Assets.HEIGHT);
		context[1] = new ClearContext(Assets.WIDTH,Assets.HEIGHT);
		context[2] = new LineContext(5,Assets.WIDTH,Assets.HEIGHT);
		context[3] = new TrailContext(Assets.WIDTH,Assets.HEIGHT,Assets.WIDTH/2,Assets.HEIGHT/2,100,90,270);
	}

	public static void adjustContextEnabled(int i, int value)
	{
		i = i -23;

		if (SHIFT && !CTRL && value == 0)
		{
			context[i].addNext();
		}
		else if(CTRL && !SHIFT && value == 0)
		{
			context[i].removeLast();
		}
		else
		{

			if (value == 0)
				cEnabled[i] = !cEnabled[i];
		}
	}

	public static void adjustSpeed(int i)
	{
		//i+1 of 127 = x of 100
		int z = 100 - (((i + 1) * 100)/127);
		SPEED = z;
		System.out.println("SPEED SET TO " + SPEED);
	}
	public static void popColor()
	{
		current = cStack.popColor();
	}
	public static void pushColor()
	{
		cStack.pushColor();
		current = cStack.getTop();

	}


}
