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

	ArrayList<Slider> sliders;
	//CButtonGroup buttons;
	//Screen screen;
	MButton switcher;
	boolean swapped; // whether screen is visible or not
	boolean[] selected ;
	ArrayList<MButton> btns;

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
		//sliders = new Slider[] {new Slider(),new Slider(),new Slider(),new Slider()};
		sliders = new ArrayList<Slider>();
		btns = new ArrayList<MButton>();
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
				else if(str.equals("skip"))
					s1.nextLine();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		selected = new boolean[sliders.size()];
		System.err.println(sliders.size() + "   " + selected.length);
	}
	private void addButton(Scanner s1)
	{
		boolean t = s1.nextInt() == 1;
		int[] b = new int[] {s1.nextInt(),s1.nextInt(),s1.nextInt(),s1.nextInt()};
		Color c = new Color(s1.nextInt(),s1.nextInt(),s1.nextInt());
		String lbl = s1.next();
		Action a = new Action(s1.nextInt(),s1.nextInt(),s1.nextInt());
		MButton bt = new MButton(a,t);
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
		else if(type.equals("color.r"))
			i = 4;
		else if(type.equals("color.g"))
			i = 5;
		else if(type.equals("color.b"))
			i = 6;
		Slider s = new Slider();

		s.setBounds(s1.nextInt(),s1.nextInt(),s1.nextInt(),s1.nextInt());
		s.setValues(s1.nextInt(),s1.nextDouble(),s1.nextDouble(),s1.nextDouble(),s1.nextDouble());
		s.setType(i);
		sliders.add(s);
	}
	public void loadDefaults()
	{

	}
	public void paint(Graphics g)
	{
		b.setColor(Color.white);
		b.fillRect(0,0,this.getWidth(),this.getHeight());

		for (Slider slider : sliders)
			slider.draw(b);
		for(MButton cb : btns)
			cb.draw(b);

		g.drawImage(buffer,0,0,getWidth(),getHeight(),null);
	}
	public void mousePressed(MouseEvent e)
	{

		for(MButton cb : btns)
		{
			if(cb.contains(e.getX(),e.getY()))
				cb.press();
		}

		for (int x = 0; x < sliders.size(); x++)
		{
			if (sliders.get(x).getBounds().contains(e.getX(), e.getY()))
				selected[x] = true;

		}


		//System.out.println("MouseDown");
		repaint();
	}
	//@Override
	@Override
	public void mouseDragged(MouseEvent e)
	{
		for(int x = 0; x < sliders.size();x ++)
			if(selected[x])
				sliders.get(x).updateValue(e.getX(),e.getY(),x);
		repaint();
	}
	@Override
	public void mouseReleased(MouseEvent e)
	{
		//selected = new boolean[] {false,false,false,false};
		int sl = selected.length;
		selected = new boolean[sl];
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
