package com.android_basics.firstname_lastname.asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class SpaceShip {

    private Bitmap bitmap;
    public Bitmap getBitmap()
    {
        return this.bitmap;
    }
    public void setBitmap(Bitmap bitmap)
    {
        this.bitmap = bitmap;
    }

    private int x;
    private int y;

    private int xVelocity;
    public void setXVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }
    public int getXVelocity()
    {
        return this.xVelocity;
    }

    public SpaceShip(Bitmap bitmap, int x, int y)
    {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
    }

    public void drawBitmap(Canvas canvas)
    {
        canvas.drawBitmap(this.bitmap, this.x, this.y,null);
        x = x + xVelocity;
    }
}
