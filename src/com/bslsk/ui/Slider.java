package com.bslsk.ui;

import com.bslsk.info.Assets;
import org.w3c.dom.css.Rect;

import java.awt.*;

public class Slider
{
	public int width,height;
    public int x,y;
    public double min, max;
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
        double calcValue = (value*(double)height)/max;
        int barX = x;
        //int barY = y+height-(int)calcValue;
        int barY = (int)calcValue;
        int barWidth = width;
        int barHeight = height - (int)calcValue;
        return new Rectangle(barX,barY,barWidth,barHeight);
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
            value = (max*ay)/height;
        }
        Assets.CONSTRAINTS[t].param = value;
    }

    public String toString()
    {
        return "x: " + x + "   y: " + y + "   w: " + width + "   h: " + height;
    }
}
