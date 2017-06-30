package com.android_basics.firstname_lastname.asteroids;

import android.graphics.Canvas;

class CanvasObject {
    // x
    protected int x;
    public int getX() {
        return this.x;
    }
    public void setX(int x) {
        this.x = x;
    }
    // y
    protected int y;
    public int getY() {
        return this.y;
    }
    public void setY(int y) {
        this.y = y;
    }
    // yVelocity
    protected int yVelocity;
    public void setYVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }
    public int getYVelocity()
    {
        return this.yVelocity;
    }
    // xVelocity
    protected int xVelocity;
    public void setXVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }
    public int getXVelocity()
    {
        return this.xVelocity;
    }

    public void drawBitmap(Canvas canvas) {}
}
