package com.android_basics.firstname_lastname.asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class SpaceShip extends CanvasBitmap{

    public SpaceShip(Bitmap bitmap, int x, int y)
    {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
    }

    @Override
    public void drawBitmap(Canvas canvas)
    {
        // Set new X - prevent ship from flying way off screen
        Integer newX = this.x + this.xVelocity;
        if (newX < 0) {
            newX = 0;
        } else if (newX > canvas.getWidth() - this.bitmap.getWidth()) {
            newX = canvas.getWidth() - this.bitmap.getWidth();
        }
        this.x = newX;
        // Set new Y
        Integer newY = this.y + this.yVelocity;
        if (newY < 0) {
            newY = 0;
        } else if (newY > canvas.getHeight() - this.bitmap.getHeight()) {
            newY = canvas.getHeight() - this.bitmap.getHeight();
        }
        this.y = newY;
        
        canvas.drawBitmap(this.bitmap, this.x, this.y,null);
    }
}
