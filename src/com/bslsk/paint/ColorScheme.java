package com.bslsk.paint;

import java.awt.Color;

public class ColorScheme
{
	public static final int SCHEME_BLACK = -1; // DEFAULT
	public static final int SCHEME_RED = 0;
	public static final int SCHEME_GREEN = 1;
	public static final int SCHEME_BLUE = 2;
	
	public static final int NEXT_NONE= -1;
	public static final int NEXT_RANDOM = 0;
	public static final int NEXT_CONT = 1;
	public static final int NEXT_SIMILAR = 2;
	
	public static final int RATE_SLOW = 0;
	public static final int RATE_NORMAL = 1;
	public static final int RATE_FAST = 2;
	  
	int scheme, movement, rate;
	  Color current;
	  public ColorScheme()
	  {
		  scheme = ColorScheme.SCHEME_BLACK;
		  movement = ColorScheme.NEXT_NONE;
		  
	  }
	  public ColorScheme(int s, int m, int r)
	  {
		  scheme = s;
		  movement = m;
		  rate = r;
		  
	  }
	  public void update()
	  {
		  if(scheme == ColorScheme.SCHEME_RED)
		  {
			  
		  }
		  else if(scheme == ColorScheme.SCHEME_GREEN)
		  {
			  
		  }
		  else if(scheme == ColorScheme.SCHEME_BLUE)
		  {
			  
		  }
	  }
	  public String toString()
	  {
		  return strings[0][scheme] + " " + strings[1][movement] + " " + strings[2][rate];
	  }
	  private static final String[][] strings = 
	  {
			  {"Red", "Green", "Blue"},
			  {"Random", "Continuous", "Similar"},
			  {"Slow", "Normal", "Fast"}
	  };
}
