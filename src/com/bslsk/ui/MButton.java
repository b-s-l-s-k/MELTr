package com.bslsk.ui;

import com.bslsk.info.Action;

import java.awt.*;

public class MButton
{
    int x,y;
    int width,height;

    Action action;
    String text;
    Color color;
    boolean TOGGLEABLE;
    boolean toggled;
    public MButton(Action a1, boolean tgl)
    {
        action = a1;
        TOGGLEABLE = tgl;
        toggled = false;
    }
    public void setStyle(String t, Color c)
    {
        text = t;
        color = c;
    }
    public void setBounds(int x1, int y1, int w,int h)
    {
        x = x1;
        y = y1;
        width = w;
        height =h;
    }
    public boolean contains(int mx, int my)
    {
        return mx < (x + width) && mx > x && my < y + height && my > y;
    }
    public void press()
    {
        action.act();
        if(TOGGLEABLE)
            toggled = !toggled;
    }
    public void draw(Graphics2D g)
    {
        g.setColor(color);
        if(TOGGLEABLE && toggled)
        {
            g.fillOval(x, y, width, height);
            g.setColor(Color.black);
            g.drawOval(x, y, width, height);
            if (color.equals(Color.black))
            {
                g.setColor(Color.white);
                g.drawString(text, x + width/3, y + (height / 2));
            }
            else
            {
                g.setColor(Color.black);
                g.drawString(text, x + width/3, y + (height / 2));
            }
        }
        else
        {
            g.fillRect(x, y, width, height);
            g.setColor(Color.black);
            g.drawRect(x, y, width, height);
            if (color.equals(Color.black))
            {
                g.setColor(Color.white);
                g.drawString(text, x + width/3, y + (height / 2));
            }
            else
            {
                g.setColor(Color.black);
                g.drawString(text, x + width/3, y + (height / 2));
            }
        }
    }
    public String toString()
    {
        return "Button: " + text + "|||  [" + x + ","+ y + "," + width + ","+ height + "]" + "\n" +
                action.toString();
    }
}
