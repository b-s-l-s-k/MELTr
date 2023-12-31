package com.bslsk.ui;

import com.bslsk.info.Action;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CButtonGroup
{
    public static final int LAYOUT_V = 0, LAYOUT_H = 1, LAYOUT_GRID = 2;

    public ArrayList<CommandButton> buttons;
    int x,y;
    int width,height;
    int layout;

    Graphics2D buffer;
    BufferedImage img;
    public CButtonGroup(int x1, int y1, int w, int h, int lo)
    {
        x = x1;
        y = y1;
        width = w;
        height =h;
        buttons = new ArrayList<CommandButton>();
        layout = lo;

        img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        buffer = (Graphics2D)img.getGraphics();
    }

    public void addButton(Action a, String text, Color color)
    {
        CommandButton ncb = new CommandButton(a);
        if(layout == LAYOUT_GRID)
        {
            int padding = width/6;
            int bW = (width/2)-(padding*2);
            int bH = bW;
            int size = buttons.size();
            int bX,bY;
            if(size % 2 == 0) //left side
                bX = padding;
            else //right side
                bX = (padding*2)+ bW;
            //int adj = size/2;
            bY = (bH+(padding*2))*(size/2)+50;
            ncb.setBounds(bX,bY,bW,bH);

        }
        ncb.setStyle(text,color);
        buttons.add(ncb);
    }
    //Precondition: contains(x,y) returned true
    public void press(int mx, int my)
    {
        int aX = mx - x;
        int aY = my - y;
        for(CommandButton c : buttons)
            if(c.contains(aX,aY))
                c.press();

    }
    public void draw(Graphics2D g)
    {
        buffer.setColor(Color.white);
        buffer.fillRect(0,0,width,height);
        for(CommandButton c : buttons)
            c.draw(buffer);
        g.drawImage(img,x,y,width,height,null);
    }
    public boolean contains(int mx, int my)
    {
        return mx < (x + width) && mx > x && my < y + height && my > y;
    }


}
