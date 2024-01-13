package com.bslsk.paint;

import com.bslsk.bin.GFrame;
import com.bslsk.info.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TreeMode extends PaintMode
{
    @Override
    public void paintTo(GFrame g)
    {
        BufferedImage n = g.iB.getSubimage(0, 0,Assets.WIDTH/2,Assets.HEIGHT);
        Graphics2D nG = (Graphics2D)n.getGraphics();
        paintRec(n,nG,3);

        if(Assets.triggers[0])
            nG.scale(Assets.CONSTRAINTS[1].param,Assets.CONSTRAINTS[1].param);
        if(Assets.triggers[1])
            nG.translate(Assets.CONSTRAINTS[2].param, Assets.CONSTRAINTS[3].param);
        if(Assets.triggers[2])
            nG.rotate(Math.toRadians(Assets.CONSTRAINTS[0].param));


        nG.drawImage(n, 0, 0, g.width/(int)(g.ratio*4), g.height/4, null);


        g.buffer.drawImage(g.iB, g.width, 0, g.width/2, g.height, 0, 0, g.width/2, g.height, null);

    }
    private void paintRec(BufferedImage n2, Graphics2D nG2, int more)
    {
        BufferedImage n = n2.getSubimage(0, 0,Assets.WIDTH/2,Assets.HEIGHT);
        Graphics2D nG = (Graphics2D)n.getGraphics();
        if(more > 0)
            paintRec(n, nG, more-1);
    }
}
