package com.bslsk.paint;

import com.bslsk.bin.GFrame;
import com.bslsk.info.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class InvertMode extends PaintMode
{
    private Random r1;
    public InvertMode()
    {
        r1 = new Random();
    }
    @Override
    public void paintTo(GFrame g)
    {

        //int i = r1.nextInt(5) + 2;
        int i = 1;
        for(int i2 = 0; i2 < i; i2++)
        {
            int nw = r1.nextInt(Assets.WIDTH / 2);
            int nx = r1.nextInt(Assets.WIDTH-nw);
            int nh = r1.nextInt(Assets.HEIGHT / 2);
            int ny = r1.nextInt(Assets.HEIGHT-nh);
            BufferedImage n = g.iB.getSubimage(nx, ny, nw, nh);
            Graphics2D nG = (Graphics2D) n.getGraphics();

            for(int x = 0; x < n.getWidth(); x ++)
                for(int y = 0; y < n.getHeight();y++)
                {
                    Color next = new Color(n.getRGB(x,y));
                    next = new Color(255 - next.getRed(),255 - next.getGreen(),255 - next.getBlue());
                    n.setRGB(x,y,next.getRGB());
                }

        }
        g.buffer.drawImage(g.iB, g.width, 0, g.width/2, g.height, 0, 0, g.width/2, g.height, null);

    }

}
