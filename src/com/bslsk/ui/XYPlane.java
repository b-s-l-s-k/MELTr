package com.bslsk.ui;

import com.bslsk.info.Assets;

import java.awt.*;

public class XYPlane
{
    public int width,height;
    public int x,y;

    public int cX,cY;
    double[] xBounds;
    double[] yBounds;
    int typeX,typeY;

    public XYPlane()
    {

    }
    public void setValues(int tx, int ty,int startx,int starty, double xmin, double xmax, double ymin, double ymax)
    {
        typeX = tx;
        typeY = ty;
        cX = startx;
        cY = starty;
        xBounds = new double[] {xmin,xmax};
        yBounds = new double[] {ymin,ymax};

    }
    public void setBounds(int x2, int y2, int w, int h)
    {
        x = x2;
        y = y2;
        width = w;
        height = h;
    }
    public void updateValue(int mx, int my, int t)
    {
        double vX;
        double vY;

        if(mx > x + width)
        {
            vX = xBounds[1];
            cX = x + width;
        }
        else if(mx < x)
        {
            vX = xBounds[0];
            cX = x;
        }
        else
        {
            double nX = mx -x;
            double rangeX = xBounds[1] - xBounds[0];
            vX = ((nX * rangeX)/width) + xBounds[0];
        }

        if(my > y + height)
        {
            vY = yBounds[1];
            cY = y + height;
        }
        else if(my < y)
        {
            vY = yBounds[0];
            cY = y;
        }
        else
        {
            double nY = my - y;
            double rangeY = yBounds[1] - yBounds[0];
            vY = ((nY * rangeY)/height) + yBounds[0];
        }
        if(typeX < 4)
        {
            //double val = value-(range/2);
            Assets.CONSTRAINTS[typeX].param = vX;
        }

        if(typeY < 4)
        {
            //double val = value-(range/2);
            Assets.CONSTRAINTS[typeY].param = vY;
        }



    }
}
