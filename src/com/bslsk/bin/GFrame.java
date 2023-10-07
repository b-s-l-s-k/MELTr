package com.bslsk.bin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
//import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import com.bslsk.gen.Effect;
import com.bslsk.gen.FilterEffect;
import com.bslsk.gen.GContext;
import com.bslsk.gen.GlitchEffect;
import com.bslsk.gen.ImageEffect;
import com.bslsk.gen.LifeThread;
import com.bslsk.gen.Shifter;
import com.bslsk.info.Assets;
import com.bslsk.info.KeyMapper;
import com.bslsk.info.Preferences;
import com.bslsk.paint.PaintBrush;
import com.bslsk.paint.Painter;

public class GFrame extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener
{
	public static final String VERSION_ID = "0";
	//private static final long serialVersionUID = 1L;
	public Graphics2D buffer;
	public BufferedImage iB;
	public Color current;
	
	Thread t1;
	Random r1;
	public ArrayList<GContext> render;
	public double angle = 0;
	public double scale = 1;
	boolean hold = false;
	public byte mode = 1;
	public SideFrame side;
	
	public int width;
	public int height;
	public double ratio;
	//final String[] presets = {"Regular","Double","2xDouble", "Quad","Glitch"}; 
	int colorMode = 1; 
	//String[] cModes = {"Black","Random Color","ProgresiveColor"};
	
	
	FilterEffect filter;
	
	Color dmC; // DRAWING MODE COLOR
		boolean[] colorchange = {false,false,false};
		boolean sbtn; // shift key down
	boolean drawingMode = false; 
	boolean mDown = false;
	BufferedImage drawI;
	Graphics2D drawG;
	
	public boolean[] triggers = {false,false,false};
	
	public int tranX = -1;
	public int tranY = -1;
	
	KeyMapper keyMap;
	public LifeThread lT;
	Thread lThread;
	
	public Painter painter; //----------------------Create Paintmodes - > Implement
	
	boolean imgMode;
	public PaintBrush brush;
	public GFrame()
	{
		super("MELTR");
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		width = gd.getDisplayMode().getWidth()/2;
		height = gd.getDisplayMode().getHeight()/2;

		Image img = Toolkit.getDefaultToolkit().createImage("res/logo.png");
		this.setIconImage(img);
		System.out.println(width + "         " + height);
		ratio = ((double)width)/((double)height);
		Assets.setGlobalConstants(width, height, ratio);
		Assets.getDefaultContraints();
		
		brush = new PaintBrush(width, height);
		
		//setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		setSize(width,height);
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//side = new SideFrame();
		
		//ratio =  width/height;
		
		r1 = new Random();
		
		brush.dmC = Color.gray;
		//drawI = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
		//drawG = (Graphics2D)drawI.getGraphics();
		//drawG.setBackground(new Color(0,0,0,0));

		filter = new FilterEffect(FilterEffect.TYPE_EDGE, 1);
			
			
		
		iB = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		buffer = (Graphics2D)iB.getGraphics();
		buffer.setColor(Color.black);
		buffer.setBackground(Color.white);
		buffer.clearRect(0, 0, width, height);
		current = Color.white;
		render = new ArrayList<GContext>();
		angle = 0;
		scale = 0;
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		
		int[] links = new int[] 
		{
			0, 1, 1, -1, 3, -1, 5, 7
		};
		painter = new Painter(Assets.getDefaultPaintModes(width, height, 250), links);
		
		keyMap = new KeyMapper("keys.txt");
		
		System.out.println();
		//lT = new LifeThread(width/10,height/10,250);
		//lThread =new Thread(lT);
		t1 = new Thread(this);
		t1.start();
		repaint();
		//lThread.start();
		
		
	}
	public void run()
	{
		while(true)
		{
			if(hold)
			try
			{
				//----------COLOR MODES----------
				if(colorMode == 0)//static black
					Assets.current = Color.black;
				else if(colorMode == 1)//random 
				{
					Assets.current = new Color(
							Assets.current.getRed() + r1.nextInt(3)-1, 
							Assets.current.getGreen() + r1.nextInt(3)-1,
							Assets.current.getBlue()+ r1.nextInt(3)-1
					);
				}
				else if(colorMode == 2)//progressive
				{
					int n = r1.nextInt(3);
					int n2 = r1.nextInt(3)-1;
					if(n == 0)
						Assets.current = new Color(Assets.current.getRed() + n2, Assets.current.getGreen(), Assets.current.getBlue());
					else if(n == 1)
						Assets.current = new Color(Assets.current.getRed() , Assets.current.getGreen()+ n2, Assets.current.getBlue());
					else if(n == 2)
						Assets.current = new Color(Assets.current.getRed() , Assets.current.getGreen(), Assets.current.getBlue()+ n2);
				
				}
			}
			catch(Exception e) 
			{
				//System.out.println("NEW COLOR GENERATED");
			}
			finally {Assets.current = new Color(r1.nextInt(256),r1.nextInt(256),r1.nextInt(256));}
			if(hold)
			{
				
				for(GContext c : render)
					c.step();
				//Shifters
				for(Shifter s : Assets.shifts)
				{
					if(s.type == Shifter.TYPE_ANGLE && s.isActive())
						Assets.CONSTRAINTS[0].param =  s.shift((Assets.CONSTRAINTS[0].param) );
					else if(s.type == Shifter.TYPE_SCALE && s.isActive())
						Assets.CONSTRAINTS[1].param = s.shift(Assets.CONSTRAINTS[1].param);
					else if(s.type == Shifter.TYPE_TRAN_X && s.isActive())
						Assets.CONSTRAINTS[2].param =  s.shift((Assets.CONSTRAINTS[2].param) );
					else if(s.type == Shifter.TYPE_TRAN_Y && s.isActive())
						Assets.CONSTRAINTS[3].param = s.shift(Assets.CONSTRAINTS[3].param);
				}
				repaint();
			}
			try {Thread.sleep(1);}
			catch(InterruptedException ie) {}
		}
	}
	public void paint(Graphics g)
	{
		
		if(buffer == null){return;}
		
		for(Effect e : Assets.effects)
			if(e.isEnabled())
				e.doEffect(buffer, iB);
		
		buffer.drawImage(brush.getImage(), 0, 0, null);
		//buffer.drawImage(Assets.BRUSH.getImage(), 0, 0, null);
		buffer.setColor(Assets.current);
		for(GContext c : render)
			c.draw(buffer);
		
		
		painter.update(this); // SEND TO PAINTER
	
		
		
		
		filter.doEffect(buffer, iB);
		g.drawImage(iB, 0, 0, width, height, null);
					
	}
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		System.out.println(e.getKeyChar() + "    " + e.getKeyCode());
		if(keyMap.keyPressed(e, this))
			return;
					/*
		if(e.getKeyChar() == 'q')
		{
			Assets.effects.get(0).toggle();
			return;
		}
		else if(e.getKeyChar() == 'w')
		{
			return;
		}
		else if(e.getKeyChar() == 'e')
		{
			return;
		}
		*/
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE && render.size() > 0)
		{	
			render.remove(render.size()-1);
			//side.removeItem();
			return;
		}
		else if(e.getKeyChar() == ' ')
		{
			if(sbtn)
				Preferences.saveState();
			else
				hold = !hold;
		}
		else if(e.getKeyChar() == 'z')
		{
			angle = 0;
			scale = 1;
			return;
		}
		else if(e.getKeyChar() == 'x')
		{
			buffer.clearRect(0, 0, 1000, 1000);
			render.clear();
			return;
		}
		else if(e.getKeyChar() == 'm') //Color mode for lines
		{
			colorMode ++;
			if(colorMode > 2)
				colorMode=0;
			//System.out.println("Color Mode: " + cModes[colorMode]);
			return;
		}

		
		
		if(e.getKeyChar() == '/')//ENABLE brush.drawingMode
		{
			brush.drawingMode = !brush.drawingMode;
			//side.setbrush.drawingMode(brush.drawingMode);
			System.out.println("Drawing mode: " + (brush.drawingMode ? "ON":"OFF"));
		}
		if(e.getKeyChar() == '.')//ENABLE brush.drawingMode
		{
			brush.imgMode = !brush.imgMode;
			//side.setbrush.drawingMode(brush.drawingMode);
			System.out.println("Image Mode: " + (imgMode ? "ON":"OFF"));
		}
		if(e.getKeyChar() == '0' && !Assets.CTRL)
			brush.colorchange[0] = !brush.colorchange[0];
		else if(e.getKeyCode() == KeyEvent.VK_MINUS && !Assets.CTRL)
			brush.colorchange[1] = !brush.colorchange[1];
		else if(e.getKeyCode() == KeyEvent.VK_EQUALS && !Assets.CTRL)
			brush.colorchange[2] = !brush.colorchange[2];
		
		if(e.getKeyChar() == '0' && Assets.CTRL)
			brush.dmC = new Color(r1.nextInt(80)+126,10,10);
		else if(e.getKeyCode() == KeyEvent.VK_MINUS && Assets.CTRL)
			brush.dmC = new Color(10,r1.nextInt(80)+126,10);
		else if(e.getKeyCode() == KeyEvent.VK_EQUALS && Assets.CTRL)
			brush.dmC = new Color(10,10,r1.nextInt(80)+126);
		
		if(e.getKeyCode() == KeyEvent.VK_SEMICOLON)
			triggers[0] = !triggers[0];
		else if(e.getKeyCode() == KeyEvent.VK_QUOTE )
			triggers[1] = !triggers[1];	
		else if(e.getKeyCode() == KeyEvent.VK_BACK_SLASH)
			triggers[2] = !triggers[2];

		//CONTROL KEY
		if(e.getKeyCode() == KeyEvent.VK_CONTROL)
		{
			sbtn = true;
			System.out.println("Shift Down");
		}
		//FAILSAFE
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			//lT.dump();
				System.exit(1);
		}
		//side.updateList(new String[] {render.size()+"", mode + "", angle + "", scale + "", colorMode + ""});
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			if(Assets.CTRL)
					new MapFrame(keyMap);
			System.out.println(
					"["+(triggers[0] ? "X":" ")+"]"+
					"["+(triggers[1] ? "X":" ")+"]"+
					"["+(triggers[2] ? "X":" ")+"]"+
					
					"TRANSITION X: " + tranX + "   Y: "+ tranY + "   Angle:" + angle + "   Scale" + scale
					);
		}
	}



	@Override
	public void mouseDragged(MouseEvent e)
	{
		brush.paint(e);
	}
	@Override
	public void mouseMoved(MouseEvent e) {	
	}
	@Override
	public void mousePressed(MouseEvent e) {brush.mDown = true;}
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		brush.mDown = false;
		if(brush.drawingMode && !Assets.CTRL)
		{
			//buffer.drawImage(drawI, 0, 0, null);
			brush.drawG.clearRect(0, 0, width, height);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) 
	{
		if(keyMap.keyReleased(e, this))
			return;
		if(e.getKeyCode() == KeyEvent.VK_CONTROL)
		{
			sbtn = false;
			Assets.CTRL = false;
			System.out.println("Shift Up");
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	
	public static void main(String[] args) 
	{ 
		new SplashScreen(); 
	//new GFrame();
	}
}
