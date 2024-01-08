package com.bslsk.ui;

import com.bslsk.info.Assets;
import org.w3c.dom.css.Rect;

import java.awt.*;

public class Slider
{
	public int width,height;
    public int x,y;
    public double min, max;
    public double step;
    public double value;
    String label;
    public int mode;
    /*
        0 = up/down regular
        1 = up/down center

    */
    private double range;
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
    public void setBounds(int x2, int y2, int w, int h)
    {
        x = x2;
        y = y2;
        width = w;
        height = h;

    }

    public Rectangle getFillBounds()
    {
        double calcValue = (value*(double)height)/range;
        int barX = x;
        //int barY = y+height-(int)calcValue;
        int barY = (int)calcValue;
        int barWidth = width;
        int barHeight = height - (int)calcValue;
        return new Rectangle(barX,barY,barWidth,barHeight);
    }
    public void draw(Graphics2D b)
    {
        b.setColor(Color.black);
        b.drawRect(x,y,width,height);
        Rectangle bounds = getFillBounds();
        b.fillRect(bounds.x,bounds.y,bounds.width,bounds.height);
        if(step > 0)
        {
            double nStep = range/step;
            int mark = height/(int)nStep;
            b.setColor(Color.white);
            for(int z = 0; z < nStep; z ++)
                b.drawLine(x,y+(z*mark),x+width,y+(z*mark));

        }
        //System.out.println(sliders[x].toString());
    }
    public Rectangle getBounds()
    {
        return new Rectangle(x,y,width,height);
    }
    public void updateValue(int mx, int my, int t)
    {
        int ay = my-y;
        if(ay < 0)
            value = max;
        else if(ay > height)
            value = min;
        else
        {
            value = ((range*ay)/height);
        }
        System.out.println(TYPES[t] + "   " + (value-(range/2)));
        Assets.CONSTRAINTS[t].param = value-(range/2);
    }

    public String toString()
    {
        return "x: " + x + "   y: " + y + "   w: " + width + "   h: " + height;
    }
    public final String[] TYPES = {"Angle", "Scale", "TranX", "TranY"};
}
