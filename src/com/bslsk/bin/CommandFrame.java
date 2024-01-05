package com.bslsk.bin;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.bslsk.info.Assets;
import com.bslsk.info.Preferences;
import com.bslsk.ui.CButtonGroup;
import com.bslsk.ui.Slider;
import com.bslsk.info.Action;
import com.bslsk.ui.TPanel;
import com.bslsk.ui.TimeLine;
import org.w3c.dom.css.Rect;

import java.io.Serial;

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
	boolean[] selected = {false,false,false,false};
	public CommandFrame(GFrame par)
	{
		super("MELTR - Command Center");
		parent = par;
		width = 1200;
		height = 800;
		setSize(width,height);
		setResizable(false);
		buffer = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		b = (Graphics2D)buffer.getGraphics();
		//Bounds for button group
		int rightSide = (width/4)*3;
		int rWidth = width-rightSide;
		int rHeight = height;

		buttons = new CButtonGroup(rightSide,0,rWidth,rHeight, CButtonGroup.LAYOUT_GRID);
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
				new Slider(1,-45,45,0), // angle
				new Slider(1,0.5,2.0,1), // scale
				new Slider(1,-3,3,0), //tran x
				new Slider(1,-3,43,0),//tran y

		};
		int nW = width - rWidth;
		int sliderW = nW/5;
		int pd = sliderW/5;
		System.out.println(nW + "     " + sliderW + "     " + pd);
		sliders[0].setBounds(pd,30,sliderW,getHeight()-60);
		sliders[1].setBounds((pd*2)+(sliderW),30,sliderW,getHeight()-60);
		sliders[2].setBounds(((pd*3)+(sliderW)*2),30,sliderW,getHeight()-60);
		sliders[3].setBounds(((pd*4)+(sliderW)*3),30,sliderW,getHeight()-60);


		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setVisible(true);
		repaint();
	}
	public void paint(Graphics g)
	{
		b.setColor(Color.white);
		b.fillRect(0,0,this.getWidth(),this.getHeight());
		b.setColor(Color.black);
		for(int x = 0; x < sliders.length;x ++)
		{
			b.drawRect(sliders[x].x,sliders[x].y,sliders[x].width,sliders[x].height);
			Rectangle bounds = sliders[x].getFillBounds();
			b.fillRect(bounds.x,bounds.y,bounds.width,bounds.height);
			//System.out.println(sliders[x].toString());
		}
		buttons.draw(b);
		g.drawImage(buffer,0,0,getWidth(),getHeight(),null);
	}
	@Override
	public void mousePressed(MouseEvent e)
	{
		if(buttons.contains(e.getX(),e.getY()))
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
