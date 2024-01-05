package com.bslsk.bin;


import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.bslsk.info.Action;
import com.bslsk.info.Key;
import com.bslsk.info.MultiKey;
import com.bslsk.ui.MButton;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class MapFrame extends JFrame implements MouseListener, KeyListener, MouseMotionListener
{
	BufferedImage screen;
	Graphics2D gr;
	Thread t1;
	MButton[][] buttons;
	ArrayList<DKey> temp;
	int[] selected = {0,0};
	
	
	public MapFrame()
	{
		super("Map Keys");
		setSize(1000,600);
		temp = new ArrayList<DKey>();
		loadFile();
		setUpButtons();
		screen = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
		gr = (Graphics2D)screen.getGraphics();
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setVisible(true);
		repaint();
	}
	private void setUpButtons()
	{
		int padding = 5; // pixel padding between buttons
		int s = (this.getWidth() - (padding*15))/14;
		buttons  = new MButton[6][14];
		for(int x = 0; x < buttons.length; x ++)
			for(int y = 0; y < buttons[x].length; y++)
			{
				try
				{
					buttons[x][y] = new MButton((s * y) + (y*padding),(x*s)+(padding*x)+50,s,padding,temp.get(x*14 + y).word,temp.get(x*y).key);
				}
				catch(Exception e) {buttons[x][y] = new MButton();}
			}
		
		
		buttons[5][3].setWeight(6, 1); // spacebar
		buttons[2][0].setWeight(1.25f, 1); // tab
		buttons[3][0].setWeight(1.25f, 1); // caps lock
		buttons[4][0].setWeight(1.1f, 1); // tab
		buttons[5][0].setWeight(1.5f, 1); // ctrl
		buttons[5][1].setWeight(1.1f, 1); // alt
		buttons[5][2].setWeight(1.5f, 1); // win/cmd
		buttons[1][13].setWeight(1.25f, 1); // backspace
		//buttons[2][13].setWeight(1, 2); // enter
		buttons[4][13].setWeight(2, 1); // r_shift
	}
	
	public void paint(Graphics g)
	{
		int xN= 0;
		int yN = 0;
		gr.setColor(Color.black);
		gr.fillRect(0, 0, getWidth(), getHeight());
		for(int x = 0; x < buttons.length; x ++)
		{
			for(int y = 0; y < buttons[x].length; y++)
			{
				if(x == selected[0] && y == selected[1])
					gr.setColor(Color.red);
				else
					gr.setColor(Color.white);
				gr.drawRect(buttons[x][y].x+xN, buttons[x][y].y, buttons[x][y].getAdjustedSizeX(), buttons[x][y].height);
				gr.drawString(buttons[x][y].word, buttons[x][y].x+xN+10, buttons[x][y].y + 10);
				//System.out.println("button " + buttons[x][y].toString());
				if(buttons[x][y].getAdjustedSizeX() != buttons[x][y].width)
					xN += buttons[x][y].getAdjustedSizeX() -  buttons[x][y].width + buttons[x][y].padding;
			}
			xN = 0;
		} 
		g.drawImage(screen, 0, 0,null);
		repaint();
	}

	
	private void loadFile()
	{
		FileInputStream fis;
		try {
			File f = new File("res/key_map.txt");
			f = f.getAbsoluteFile();
			fis = new FileInputStream(f);
			Scanner s1 = new Scanner(fis);
			while(s1.hasNext()) // LAYOUT: [keychar] [type] [sType] [dType] 
			{
				String w = s1.next();
				int code = s1.nextInt();
				temp.add(new DKey(w,code));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}
	private int[] options1()
	{
		int[] result = new int[] {-1,-1,-1};
		String[] choices = 	
			{
					"ADD_CONTEXT",
					"REMOVE_CONTEXT",
					"SET_SETTING",
					"SET_DRAW", 
					"SET_EFFECT", 
					"SET_SHIFTER",
					"KEY_DOWN", 
					"MOVE_IMG", 
					"COLOR_CHG"
			};
		String selectedValue = (String)JOptionPane.showInputDialog(null,
	             "Select an Action Type for Button: [" + buttons[selected[0]][selected[1]].key + "]", "Add Action",
	             JOptionPane.QUESTION_MESSAGE, null,
	             choices, choices[0]);
		for(int x = 0; x < choices.length;x++)
			if(selectedValue != null && selectedValue.equals(choices[x]))
			{
					result = options2(choices, x);
					break;
			}
		return result;
	}
	/**
	 * @param prev String array of previous choices
	 * @param pC "previous Choice"
	 */
	private int[] options2(String[] prev, int pC)
	{
		if(pC == 1 || pC == 3 || pC == 4 || pC == 6 || pC == 8)
			return new int[] {pC,-1,-1};
		
		int[] result = new int[3];
		String[] choices = new String[] {"N/A"};
		if(pC == 0) // add
		{
			choices = new String[]	
			{
					"CONTEXT_LINE",
					"CONTEXT_CLEAR"  , 						
					"CONTEXT_COLOR" ,
					"COTEXT_GLITCH",
					"CONTEXT_WORD",
					"CONTEXT_IMAGE" ,
					"CONTEXT_CIRCLE"
					
			};
		}
		else if(pC == 1) // remove
		{
			choices = new String[]	
					{
						"REMOVE_CONTEXT"
					};
		}
		else if(pC == 2) // settings
		{
			choices = new String[]	
			{
				"CHANGE_ANGLE",
				"CHANGE_SCALE",
				"CHANGE_TRANX",
				"CHANGE_TRANY",
				"CHANGE_DRAWTOGGLE",
				"SETTING_ANGLE",
				"SETTING_SCALE",
				"SETTING_TRANX",
				"SETTING_TRANY",
				"SETTING_DRAWTOGGLE"
			};
			
		}
		else if(pC == 3) // draw
		{
			choices = new String[]	
			{
					"DRAW_NORMAL",
					"DRAW_DOUBLE",
					"DRAW_2XDOUBLE",
					"DRAW_QUAD",
					"DRAW_GLITCH" ,
					"DRAW_LIFE" ,
					"DRAW_BURST",
					"DRAW_DIST",
					"DRAW_CENTER",
				
			};
		}
		else if(pC == 4) // effect
		{
			choices = new String[]	
			{
					"EFFECT_GLITCH",
					"EFFECT_IMAGE"
			};
		}
		else if(pC == 5) // shifter
		{
			choices = new String[]	
			{
							"ANGLE",
							"SCALE",
							"TRAN_X",
							"TRAN_Y"
			};
		}
		else if(pC == 6) // key down
		{
			
		}
		else
			choices = new String[] {"N/A"};
		
		String selectedValue = (String)JOptionPane.showInputDialog
		(
				 null,
	             "Select an Sub-Type for Type [" + prev[pC] + "]", 
	             "Add Action",
	             JOptionPane.QUESTION_MESSAGE, 
	             null,
	             choices, 
	             choices[0]
		 );
			if(selectedValue == null)
				result = new int[] {-1,-1,-1};
		for(int x = 0; x < choices.length;x++)
			if(selectedValue.equals(choices[x]))
			{
					result = options3(choices, x, pC);
					break;
			}
		return result;
	}
	
	
	private int[] options3(String[] prev, int pC, int fC)
	{
		if(fC == 0 || fC == 3)
			return new int[] {fC, pC, -1};
		else 
		{
			return new int[] {fC, pC, -1}; //PLACEHOLDER FOR NOW, MUST CHANGE :TODO:
		}

	}
	
	
	@Override
	public void mouseReleased(MouseEvent e) 
	{	
		for(int x = 0; x < buttons.length; x ++)
			for(int y = 0; y < buttons[x].length; y++)
				if(buttons[x][y].isInside(e))
				{
					selected[0] = x;
					selected[1] = y;
				}
		int[] d = options1();
		
	}
	
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

	
	@Override
	public void mouseDragged(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) 
	{
		for(int x = 0; x < buttons.length; x ++)
			for(int y = 0; y < buttons[x].length; y++)
				if(buttons[x][y].isInside(e))
				{
					selected[0] = x;
					selected[1] = y;
				}
		repaint();
	}
//---------------------------------------------------------------------------------------
	private class DKey
	{
		public String word;
		public int key;
		public int[] types;
		public DKey(String w, int k)
		{
			word = w;
			key = k;
		}
		public void setTypes(int t, int st, int dt)
		{
			types = new int[] {t,st,dt};
		}
		public Key convertToKey()
		{
			if(types == null) 
					return new Key(key,new Action(-1,-1,-1));
			
			return new Key(key, new Action(types[0],types[1],types[2]));
		}
	}

}
