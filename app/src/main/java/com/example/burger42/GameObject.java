package com.example.burger42;

import android.graphics.Bitmap;

public interface GameObject {

    public void start();

    public void update(double deltaSeconds);

    public int xPosition();

    public int yPosition();

    public Bitmap texture();
}
