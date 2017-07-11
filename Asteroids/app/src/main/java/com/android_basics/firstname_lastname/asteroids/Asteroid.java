package com.android_basics.firstname_lastname.asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class Asteroid extends CanvasBitmap {

    private static Random generator;

    public Asteroid(Bitmap bitmap, int x, int y)
    {
        if (generator == null) {
            generator = new Random();
        }

        this.bitmap = bitmap;
        this.x = x;
        this.y = y;

        if (generator.nextBoolean()) {
            this.yVelocity = 12;
            this.xVelocity = 2;
            if (generator.nextBoolean()) {
                this.xVelocity*= -1;
            }
        } else {
            this.yVelocity = 10;
            this.xVelocity = 1;
            if (generator.nextBoolean()) {
                this.xVelocity*= -1;
            }
        }
    }

    @Override
    public void drawBitmap(Canvas canvas)
    {
        canvas.drawBitmap(this.bitmap, this.x, this.y, null);
        this.x = this.x + this.xVelocity;
        this.y = this.y + this.yVelocity;
    }

    @Override
    public boolean checkRemove(Canvas canvas) {
        if (this.y > canvas.getHeight()) {
            return true;
        }
        return false;
    }
}
