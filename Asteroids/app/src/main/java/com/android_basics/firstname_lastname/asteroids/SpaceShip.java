package com.android_basics.firstname_lastname.asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by dimit on 6/25/2017.
 */

public class SpaceShip {

    private Bitmap bitmap;
    private int x;
    private int y;
    private int xVelocity;

    public SpaceShip(Bitmap bitmap, int x, int y)
    {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
    }
    public Bitmap getBitmap()
    {
        return this.bitmap;
    }
    public void setBitmap(Bitmap bitmap)
    {
        this.bitmap = bitmap;
    }
    public void drawBitmap(Canvas canvas)
    {
        canvas.drawBitmap(this.bitmap, this.x, this.y,null);
        x = x + xVelocity;
    }
}
