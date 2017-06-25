package com.android_basics.firstname_lastname.asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Asteroid {

    // Bitmap
    private Bitmap bitmap;
    public Bitmap getBitmap() {
        return this.bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    // x
    private int x;
    public int getX() {
        return this.x;
    }
    public void setX(int x) {
        this.x = x;
    }
    // y
    private int y;
    public int getY() {
        return this.y;
    }
    public void setY(int y) {
        this.y = y;
    }
    // yVelocity
    private int yVelocity = 10; // inital value
    public void setYVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }
    public int getYVelocity()
    {
        return this.yVelocity;
    }

    public Asteroid(Bitmap bitmap, int x, int y)
    {
        this.bitmap = bitmap;
        
        this.x = x;
        this.y = y;
    }

    public void drawBitmap(Canvas canvas)
    {
        canvas.drawBitmap(this.bitmap, this.x, this.y, null);
        y = y + yVelocity;
    }
}
