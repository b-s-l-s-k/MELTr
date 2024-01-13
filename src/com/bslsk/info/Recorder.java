package com.bslsk.info;

import java.awt.*;
import java.util.ArrayList;

public class Recorder
{
    boolean started;
    ArrayList<Double> values;
    public int constraint;

    boolean running;
    int count;
    public Recorder(int con)
    {
        constraint = con;
        values = new ArrayList<Double>();
        count = 0;
    }
    public void act()
    {
        if(count < values.size())
        {
            if(constraint < 4)
            {
                Assets.CONSTRAINTS[constraint].param = values.get(count).doubleValue();
                System.out.println("Changed to Value " + values.get(count));
            }
            else if(constraint == 4)
            {
                int rgb = values.get(count).intValue();
                int r = rgb/1000000;
                int g = (rgb%1000000)/1000;
                int b = rgb%1000;
                Assets.current = new Color(r,g,b);
                System.out.println("Color Value Loaded: " + r + "," + g + "," + b);
            }
            count++;
        }
        else
            count = 0;
    }
    public void updateRec()
    {
        if(started)
        {
            if(constraint < 4)
            {
                values.add(Assets.CONSTRAINTS[constraint].param);
                System.out.println("Added Value " + values.getLast());
            }
            else if(constraint == 4)
            {
                int r = Assets.current.getRed();
                int g = Assets.current.getGreen();
                int b = Assets.current.getBlue();
                g *= 1000;
                r *= 1000000;
                double rgb = r+g+b;
                values.add(rgb);
                System.out.println("Color Value " + (int)rgb);
                //values.add(Assets.current);
            }
        }
    }
    public void startEvent()
    {
        running = true;
    }
    public void stopEvent()
    {
        running = false;
    }
    public void startRec()
    {
        started = true;
    }
    public void stopRec()
    {
        started = false;
    }

    public void toggleRec()
    {
        started = !started;
        if (started)
        {
            values = new ArrayList<Double>();
            System.out.println("Recorder " + constraint + " has STARTED recording");
        }
        else
        {
            System.out.println("Recorder " + constraint + " has STOPPED recording");
        }
    }

    public void toggleEvent()
    {
        running = !running;
        if (running)
        {
            System.out.println("Recorder " + constraint + " has STARTED RUNNING");
        }
        else
        {
            System.out.println("Recorder " + constraint + " has STOPPED RUNNING");
        }
    }

    public boolean isStarted()
    {
        return started;
    }

    public boolean isRunning()
    {
        return running;
    }
}
