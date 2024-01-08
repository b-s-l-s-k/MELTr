package com.bslsk.ui;

import com.bslsk.info.Action;

import java.awt.*;

public class Screen
{
    int width, height;
    //x and y should be 0

    CButtonGroup buttons;

    public Screen(int w, int h)
    {
        width = w;
        height = h;
        buttons = new CButtonGroup(0,0,width,height, new int[] {3,3});
        /*
        65 0 0 -1
        83 0 1 -1
        68 0 2 -1
        70 0 3 -1
        71 0 4 -1
        72 0 5 -1
        74 0 6 5
        */

        buttons.addButton2(
                new Action(0,0,-1),
                "Line",
                Color.black
        );
        buttons.addButton2(
                new Action(0,1,-1),
                "Clear",
                Color.white
        );
        buttons.addButton2(
                new Action(0,2,-1),
                "Box",
                Color.red
        );
        buttons.addButton2(
                new Action(0,3,-1),
                "Spike",
                Color.orange
        );
        buttons.addButton2(
                new Action(0,4,-1),
                "Word",
                Color.cyan
        );
        buttons.addButton2(
                new Action(0,5,-1),
                "IDK",
                Color.blue
        );
        buttons.addButton2(
                new Action(0,6,-1),
                "Circle",
                Color.magenta
        );
    }
    public void draw(Graphics2D b)
    {
        buttons.draw(b);
    }
    public void press(int x1,int y1)
    {
        buttons.press(x1,y1);
    }

}
