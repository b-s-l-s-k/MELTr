package com.bslsk.midi;

import com.bslsk.info.Action;
import com.bslsk.info.Assets;

import java.awt.*;

public class MidiAction
{
    public static final int ANGLE = 0, SCALE = 1, TRANX = 2, TRANY = 3, CONTEXT = 4, SPEED = 5, OPACITY = 6, FILTERTYPE = 7;
    //CONTEXT - STYPE
    public static final int C_MODIFY = 0, C_ENABLED = 1, C_COLOR = 2, C_STACK = 3;
    private int type,stype,dtype;
    public MidiAction(int t, int s, int d)
    {
        type = t;
        stype = s;
        dtype = d;
    }

    public void act(int value)
    {
        if(type == ANGLE)
        {
            int[] bounds = Assets.CONSTRAINTS[type].getBounds();
            int range = bounds[1] - bounds[0];
            double z = (((double)(range * value))/127.0);
            z += bounds[0];
            Assets.CONSTRAINTS[type].param = z;
            return;
        }
        else if(type == SCALE)
        {
            int[] bounds = Assets.CONSTRAINTS[type].getBounds();
            int range = bounds[1] - bounds[0];
            double z = (((double)(range * value))/127.0);
            z += bounds[0];
            Assets.CONSTRAINTS[type].param = z;
            return;
        }
        else if(type == TRANX)
        {
            int[] bounds = Assets.CONSTRAINTS[type].getBounds();
            int range = bounds[1] - bounds[0];
            double z = (((double)(range * value))/127.0);
            z += bounds[0];
            Assets.CONSTRAINTS[type].param = z;
            return;
        }
        else if(type == TRANY)
        {
            int[] bounds = Assets.CONSTRAINTS[type].getBounds();
            int range = bounds[1] - bounds[0];
            double z = (((double)(range * value))/127.0);
            z += bounds[0];
            Assets.CONSTRAINTS[type].param = z;
            return;
        }
        //-----------------------------------------------------------------------------
        //CONTEXT SETTINGS
        //-----------------------------------------------------------------------------
        if(type == CONTEXT)
        {
            if(stype == C_MODIFY)
                Assets.context[dtype].modify(value);
            else if(stype == C_ENABLED && value == 0)
            {
                if(Assets.SHIFT && !Assets.CTRL)
                    Assets.context[dtype].addNext();
                else if(!Assets.SHIFT && Assets.CTRL)
                    Assets.context[dtype].removeLast();
                else
                    Assets.cEnabled[dtype] = !Assets.cEnabled[dtype];
            }
            else if(stype == C_COLOR && value == 0)
            {
                if(dtype != 0)
                {
                    if(dtype < 0)
                        Assets.context[Math.abs(dtype)].popColor();
                    else
                        Assets.context[Math.abs(dtype)].pushColor();
                }
                else // 0 value sets System Color (current) to black
                {
                    Assets.current = Color.black;
                }
            }
            else if(stype == C_STACK && value == 0)
            {
                if(dtype == 0)
                    Assets.cStack.popColor();
                else
                    Assets.cStack.pushColor();

                Assets.current = Assets.cStack.getTop();
            }
        }
        //-----------------------------------------------------------------------------
        //SPEED
        //-----------------------------------------------------------------------------
        if(type == SPEED)
        {
            int z = 100 - (((value + 1) * 100)/127);
            Assets.SPEED = z;
            System.out.println("SPEED SET TO " + SPEED);
        }
        else if(type == OPACITY)
        {
            Assets.DRAW_OPACITY = (value * 100.0f) / 12700.0f ;
            System.out.println("OPACITY SET TO " + Assets.DRAW_OPACITY);
        }
        else if(type == FILTERTYPE)
        {
            if(stype == 0)
            {
                if (value != 0)
                {
                    Assets.filter.type = (((value) * 3) / 127);
                    //Assets.filter.active = true;
                    System.out.println("FILTER TYPE SET TO " + Assets.filter.type);
                }
                else
                {

                    //Assets.filter.active = false;
                }
            }
            else if(stype == 1)
            {
                Assets.filter.strength = value;
                System.out.println("FILTER TYPE SET TO " + Assets.filter.strength);
            }
            else if(stype == 2)
            {
                if(value == 0)
                {
                    Assets.filter.active = !Assets.filter.active;
                    System.out.println("FILTER " + (Assets.filter.active ? "Active" : "Inactive"));
                }

            }


        }
    }
}
