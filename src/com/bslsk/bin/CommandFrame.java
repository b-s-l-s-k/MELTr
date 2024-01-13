package com.bslsk.bin;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;


import javax.swing.JFrame;

import com.bslsk.info.Assets;
import com.bslsk.paint.PaintMode;
import com.bslsk.ui.*;
import com.bslsk.info.Action;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Scanner;

public class CommandFrame extends JFrame implements MouseListener, MouseMotionListener,KeyListener
{

	@Serial
	private static final long serialVersionUID = -8726129841735251587L;
	int width,height;
	GFrame parent;
	BufferedImage buffer;
	Graphics2D b;

	Slider[] sliders;
	CButtonGroup buttons;
	Screen screen;
	CommandButton switcher;
	boolean swapped; // whether screen is visible or not
	boolean[] selected = {false,false,false,false};
	ArrayList<CommandButton> btns;

	public CommandFrame(GFrame par, boolean custom)
	{
		super("MELTR - Command Center");
		parent = par;
		width = 1200;
		height = 900;
		setSize(width,height);
		setResizable(false);
		buffer = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		b = (Graphics2D)buffer.getGraphics();
		//Bounds for button group
		if(custom)
			loadCustom();
		else
			loadDefaults();


		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setVisible(true);
		repaint();
	}

	private void loadCustom()
	{
		sliders = new Slider[] {new Slider(),new Slider(),new Slider(),new Slider()};
		btns = new ArrayList<CommandButton>();
		FileInputStream fis;
		try {
			File f = new File("res/custom_frame.txt");
			f = f.getAbsoluteFile();
			fis = new FileInputStream(f);
			Scanner s1 = new Scanner(fis);
			while(s1.hasNext()) // LAYOUT: [keychar] [type] [sType] [dType]
			{
				String str = s1.next();
				if(str.equals("button"))
					addButton(s1);
				else if(str.equals("slider"))
					addSlider(s1);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void addButton(Scanner s1)
	{
		boolean t = s1.nextInt() == 1;
		int[] b = new int[] {s1.nextInt(),s1.nextInt(),s1.nextInt(),s1.nextInt()};
		Color c = new Color(s1.nextInt(),s1.nextInt(),s1.nextInt());
		String lbl = s1.next();
		Action a = new Action(s1.nextInt(),s1.nextInt(),s1.nextInt());
		CommandButton bt = new CommandButton(a,t);
		bt.setBounds(b[0],b[1],b[2],b[3]);
		bt.setStyle(lbl,c);
		System.out.println(bt.toString());
		btns.add(bt);

	}
	private void addSlider(Scanner s1)
	{
		String type = s1.next().toLowerCase();
		int i = 0;
		if(type.equals("angle"))
			i = 0;
		else if(type.equals("scale"))
			i = 1;
		else if(type.equals("tranx"))
			i = 2;
		else if(type.equals("trany"))
			i = 3;

		sliders[i].setBounds(s1.nextInt(),s1.nextInt(),s1.nextInt(),s1.nextInt());
		sliders[i].setValues(s1.nextInt(),s1.nextDouble(),s1.nextDouble(),s1.nextDouble(),s1.nextDouble());
	}
	public void loadDefaults()
	{
		int rightSide = (width/4)*3;
		int rWidth = width-rightSide;
		int rHeight = height;
		buttons = new CButtonGroup(rightSide,0,rWidth,rHeight, new int[] {3,3});
		//49-54 keys
		//3 0 -1 through 3 5 -1
		buttons.addButton(
				new Action(3,0,-1),
				"Nor",
				Color.white
		);
		buttons.addButton(
				new Action(3,1,-1),
				"Ref",
				Color.red
		);
		buttons.addButton(
				new Action(3,2,-1),
				"xRef",
				Color.orange
		);
		buttons.addButton(
				new Action(3,3,-1),
				"Quad",
				Color.blue
		);
		buttons.addButton(
				new Action(3,4,-1),
				"Gli",
				Color.pink
		);
		buttons.addButton(
				new Action(3,5,-1),
				"Melt",
				Color.cyan
		);
		sliders = new Slider[]
				{
						new Slider(1,-45,45,0,1), // angle
						new Slider(1,0.5,2.0,1,.05), // scale
						new Slider(1,-3,3,0,1), //tran x
						new Slider(1,-3,3,0,1),//tran y

				};
		int nW = width - rWidth;
		int sliderW = nW/5;
		int pd = sliderW/5;
		System.out.println(nW + "     " + sliderW + "     " + pd);
		sliders[0].setBounds(pd,30,sliderW,getHeight()-60);
		sliders[1].setBounds((pd*2)+(sliderW),30,sliderW,getHeight()-60);
		sliders[2].setBounds(((pd*3)+(sliderW)*2),30,sliderW,getHeight()-60);
		sliders[3].setBounds(((pd*4)+(sliderW)*3),30,sliderW,getHeight()-60);

		screen = new Screen(width,height);
		switcher = new CommandButton(null,false);
		switcher.setStyle("Switch", Color.YELLOW);
		switcher.setBounds(width-100,height-100,100,100);
		swapped = false;
	}
	public void paint(Graphics g)
	{
		b.setColor(Color.white);
		b.fillRect(0,0,this.getWidth(),this.getHeight());

		for (Slider slider : sliders)
			slider.draw(b);
		for(CommandButton cb : btns)
			cb.draw(b);

		g.drawImage(buffer,0,0,getWidth(),getHeight(),null);
	}
	public void paint2(Graphics g)
	{
		b.setColor(Color.white);
		b.fillRect(0,0,this.getWidth(),this.getHeight());
		//b.setColor(Color.black);
		if(!swapped)
		{
			for (Slider slider : sliders)
				slider.draw(b);
			buttons.draw(b);
		}
		else
		{
			screen.draw(b);
		}
		switcher.draw(b);
		g.drawImage(buffer,0,0,getWidth(),getHeight(),null);
	}
	public void mousePressed(MouseEvent e)
	{

		for(CommandButton cb : btns)
		{
			if(cb.contains(e.getX(),e.getY()))
				cb.press();
		}

		for (int x = 0; x < sliders.length; x++)
		{
			if (sliders[x].getBounds().contains(e.getX(), e.getY()))
				selected[x] = true;

		}


		//System.out.println("MouseDown");
		repaint();
	}
	//@Override
	public void mousePressed2(MouseEvent e)
	{
		if(switcher.contains(e.getX(),e.getY()))
		{
			swapped = !swapped;
		}
		if(!swapped)
		{
			if (buttons.contains(e.getX(), e.getY()))
			{
				buttons.press(e.getX(), e.getY());
				return;
			}
			else
			{
				for (int x = 0; x < sliders.length; x++)
				{
					if (sliders[x].getBounds().contains(e.getX(), e.getY()))
						selected[x] = true;

				}
			}
		}
		else
		{
			screen.press(e.getX(),e.getY());
		}
		//System.out.println("MouseDown");
		repaint();
	}
	@Override
	public void mouseDragged(MouseEvent e)
	{
		for(int x = 0; x < sliders.length;x ++)
			if(selected[x])
				sliders[x].updateValue(e.getX(),e.getY(),x);
		repaint();
	}
	@Override
	public void mouseReleased(MouseEvent e)
	{
		selected = new boolean[] {false,false,false,false};
	}




	@Override
	public void mouseClicked(MouseEvent e)
	{

	}



	@Override
	public void mouseEntered(MouseEvent e)
	{

	}

	@Override
	public void mouseExited(MouseEvent e)
	{

	}



	@Override
	public void mouseMoved(MouseEvent e)
	{

	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		parent.keyTyped(e);
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		parent.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		parent.keyReleased(e);
	}
}
