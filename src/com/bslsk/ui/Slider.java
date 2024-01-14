package com.bslsk.ui;

import com.bslsk.info.Assets;
import org.w3c.dom.css.Rect;

import java.awt.*;

public class Slider
{
	public int width,height;
    public int x,y;
    int fillY, fillH;
    public double min, max;
    public double step;
    public double value;
    String label;
    public int mode;

    int type;
    /*
        0 = up/down regular
        1 = up/down center

    */
    private double range;
    public Slider()
    {

    }
    public Slider(int modeI, double minI, double maxI, double valueI )
    {
        mode= modeI;
        min = minI;
        max = maxI;
        value = valueI;

        range = Math.abs(min) + Math.abs(max);
        step = 0;
    }
    public Slider(int modeI, double minI, double maxI, double valueI ,double stp)
    {
        mode= modeI;
        min = minI;
        max = maxI;
        value = valueI;

        range = Math.abs(min) + Math.abs(max);
        step = stp;
    }
    public void setType(int t)
    {
        type = t;
    }
    public void setValues(int modeI, double minI, double maxI, double valueI ,double stp)
    {
        mode= modeI;
        min = minI;
        max = maxI;
        value = valueI;

        range = Math.abs(min) + Math.abs(max);
        step = stp;
        fillY = 0;
        fillH = 0;
    }
    public void setBounds(int x2, int y2, int w, int h)
    {
        x = x2;
        y = y2;
        width = w;
        height = h;

    }

    public Rectangle getFillBounds()
    {
        return new Rectangle(x,fillY,width,fillH);
    }
    public void draw(Graphics2D b)
    {
        b.setColor(Color.black);
        b.drawRect(x,y,width,height);
        Rectangle bounds = getFillBounds();
        setColor(b);

        b.fillRect(bounds.x,bounds.y,bounds.width,bounds.height);
        if(step > 0 && type < 4)
        {
            double nStep = range/step;
            int mark = height/(int)nStep;
            b.setColor(Color.white);
            for(int z = 0; z < nStep; z ++)
                b.drawLine(x,y+(z*mark),x+width,y+(z*mark));

        }
        //System.out.println(sliders[x].toString());
    }

    private void setColor(Graphics2D b)
    {
        if(type ==4)
        {
            b.setColor(new Color(Assets.current.getRed(),0,0));
        }
        else if(type ==5)
        {
            b.setColor(new Color(0,Assets.current.getGreen(),0));
        }
        else if(type ==6)
        {
            b.setColor(new Color(0,0,Assets.current.getBlue()));
        }
    }

    public Rectangle getBounds()
    {
        return new Rectangle(x,y,width,height);
    }
    public void updateValue(int mx, int my, int t)
    {
        int rY = my - y;

        if(my < y)
        {
            value = min;
            fillY = y;
            fillH = height;

        }
        else if(my> y + height)
        {
            value = max;
            fillY = y+height;
            fillH = 1;
        }
        else
        {
            double nV = (((double)rY)*range)/(double)height;
            if(type < 4)
                nV += min;
            value = nV;
            fillY = my;
            fillH = (y+height)-my;
        }

        //value = 0;
        //System.out.println(TYPES[t] + "   " + (value-(range/2)));

        if(type < 4)
        {
            double val = value-(range/2);
            Assets.CONSTRAINTS[t].param = (val) - (val % step);
        }
        else if (type <=6)
        {
            System.out.println(value+"");
            if (type ==4)
                Assets.current = new Color((int)value, Assets.current.getGreen(),Assets.current.getBlue());
            if (type ==5)
                Assets.current = new Color( Assets.current.getRed(),(int)value,Assets.current.getBlue());
            if (type ==6)
                Assets.current = new Color(Assets.current.getRed(), Assets.current.getGreen(),(int)value);
        }


    }

    public String toString()
    {
        return "x: " + x + "   y: " + y + "   w: " + width + "   h: " + height;
    }
    public final String[] TYPES = {"Angle", "Scale", "TranX", "TranY"};
}
