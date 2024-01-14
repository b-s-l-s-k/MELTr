package com.bslsk.paint;

import com.bslsk.bin.GFrame;
import com.bslsk.info.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ImageMode extends PaintMode
{
    int swap;
    int itv;
    int type; // 0 1 2
    BufferedImage use;
    public ImageMode()
    {
        itv = 30;
        swap = 0;
        use = Assets.loadedIMG[1];
        //initDistort();
    }
    public final int threshold = 384;
    private void initDistort()
    {
        if(use == null)
            return;
        for(int x = 0; x < use.getWidth(); x ++)
            for(int y = 0; y < use.getHeight();y++)
            {
                Color next = new Color(use.getRGB(x,y));
                if(next.getRed() + next.getGreen() + next.getBlue() >= threshold)
                    use.setRGB(x,y,Color.white.getRGB());//use.setRGB(x,y,new Color(0,0,0,0).getRGB());//
                else
                    use.setRGB(x,y,Color.black.getRGB());
            }

    }

    @Override
    public void paintTo(GFrame g)
    {
        BufferedImage n = g.iB.getSubimage(0, 0, Assets.WIDTH  , Assets.HEIGHT);
        Graphics2D nG = (Graphics2D) n.getGraphics();
        //if(Assets.triggers[2])
            nG.rotate(Math.toRadians(Assets.CONSTRAINTS[0].param),Assets.WIDTH/2,Assets.HEIGHT/2);
        //if(Assets.triggers[0])
            nG.scale(Assets.CONSTRAINTS[1].param,Assets.CONSTRAINTS[1].param);
        //if(Assets.triggers[1])
            //nG.translate(Assets.CONSTRAINTS[2].param, Assets.CONSTRAINTS[3].param);
        //int tX =  Assets.WIDTH - (int)(Assets.WIDTH * Assets.CONSTRAINTS[1].param/2.0);
        //int tY = Assets.HEIGHT - (int)(Assets.HEIGHT * Assets.CONSTRAINTS[1].param/2.0);
        /*
        g.buffer.drawImage(n, tX,tY,
                (int)(Assets.WIDTH * Assets.CONSTRAINTS[1].param/2.0),
                (int)(Assets.HEIGHT * Assets.CONSTRAINTS[1].param/2.0),
                null);
        */
        g.buffer.drawImage(n, 0,0,
                n.getWidth(),
                n.getHeight(),
                null);
        if(swap >= itv)
        {
            swap = 0;

        }
        else
        {
            swap++;

        }
        g.buffer.fillRect((Assets.WIDTH/2) - 200,
                (Assets.HEIGHT/2) - 200,
                400,
                400);
        /*
        g.buffer.drawImage(use,
                (Assets.WIDTH/2) - 200,
                (Assets.HEIGHT/2) - 200,
                400,
                400,
                null);
        */
    }

}

