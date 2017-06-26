package com.android_basics.firstname_lastname.asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Asteroid extends CanvasBitmap {

    public Asteroid(Bitmap bitmap, int x, int y)
    {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;

        this.yVelocity = 10;
    }

    public void drawBitmap(Canvas canvas)
    {
        canvas.drawBitmap(this.bitmap, this.x, this.y, null);
        this.y = this.y + this.yVelocity;
    }
}
