package com.bslsk.ui;

import com.bslsk.info.Action;

import java.awt.*;

public class CommandButton
{
    int x,y;
    int width,height;

    Action action;
    String text;
    Color color;
    public CommandButton(Action a1)
    {
        action = a1;
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
    }
    public void draw(Graphics2D g)
    {
        g.setColor(color);
        g.fillRect(x,y,width,height);
        g.setColor(Color.black);
        g.drawRect(x,y,width,height);
    }
}
