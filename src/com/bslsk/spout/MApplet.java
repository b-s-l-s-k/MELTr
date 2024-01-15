package com.bslsk.spout;
import com.bslsk.info.Assets;
import processing.core.PConstants;
import processing.core.PImage;
import spout.*;
import processing.core.PApplet;

import java.awt.image.BufferedImage;

public class MApplet extends PApplet
{
    Spout spout;
    public PImage img;
    public MApplet()
    {
        img = createImage(Assets.WIDTH,Assets.HEIGHT,PConstants.ARGB);
        height = Assets.HEIGHT;
        width = Assets.WIDTH;
    }
    @Override
    public void setup()
    {
        setSize(Assets.WIDTH,Assets.HEIGHT);
        spout = new Spout(this);
        spout.createSender("MELTr");

    }
    public void settings()
    {
        size(Assets.WIDTH,Assets.HEIGHT, PConstants.P2D);
    }
    @Override
    public void draw()
    {
        /*
        fill(0,5);
        rect(0,0,width,height);
        fill(200,30,30);
        ellipse(mouseX,mouseY,100,100);
        */
        image(img,0,0,width,height);
        spout.sendTexture();
    }

    public void updateImage(BufferedImage bimg)
    {
        img =new PImage(bimg.getWidth(),bimg.getHeight(),PConstants.ARGB);
        bimg.getRGB(0, 0, img.width, img.height, img.pixels, 0, img.width);
        img.updatePixels();
    }
}
