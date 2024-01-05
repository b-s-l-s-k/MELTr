package com.bslsk.gen.filter;

import java.awt.image.BufferedImage;

public abstract class Filter
{
    public abstract void doFilter(BufferedImage buffer);

    public abstract void changeSettings(int a, int b);
}
