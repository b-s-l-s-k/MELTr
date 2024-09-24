package com.bslsk.util;

import com.bslsk.info.Assets;

import java.awt.*;
import java.util.Stack;

public class ColorStack
{   Stack<Color> cStack;
    public ColorStack()
    {
        cStack = new Stack<Color>();
        cStack.add(new Color(Assets.R.nextInt(255),Assets.R.nextInt(255),Assets.R.nextInt(255)));
    }
    public Color popColor()
    {
        if(cStack.size() > 1)
            return cStack.pop();
        else
            return Assets.current;
    }
    public void pushColor()
    {
        cStack.push(new Color(Assets.R.nextInt(255),Assets.R.nextInt(255),Assets.R.nextInt(255)));
        //Assets.current = c;

    }
    /*
    public void pushColor()
    {
        int w = Assets.R.nextInt(3);
        Color c;
        if(w == 0)
            c = new Color(Assets.R.nextInt(255),0,0);
        else if(w == 1)
            c = new Color(0, Assets.R.nextInt(255),0);
        else
            c = new Color(0,0,Assets.R.nextInt(255));
        cStack.push(c);
        //Assets.current = c;

    }
    */
    public Color getTop()
    {
        return cStack.getLast();
    }
}
