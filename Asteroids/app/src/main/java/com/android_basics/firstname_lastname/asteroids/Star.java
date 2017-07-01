package com.android_basics.firstname_lastname.asteroids;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

public class Star extends CanvasObject {

    private float radius;
    private Paint paint;

    public Star(int x, int y) {
        this.x = x;
        this.y = y;

        this.paint = new Paint();
        this.paint.setColor(Color.WHITE);

        //
        Random generator = new Random();
        if (generator.nextFloat() > 0.5f) {
            this.yVelocity = 5;
            this.radius = 2.0f;
        } else {
            this.yVelocity = 10;
            this.radius = 5.0f;
        }
    }

    @Override
    public void drawBitmap(Canvas canvas) {
        this.y = this.y + this.yVelocity;
        canvas.drawCircle((float)this.x, (float)this.y, this.radius, paint);
    }

    @Override
    public boolean checkRemove(Canvas canvas) {
        if (this.y > canvas.getHeight()) {
            return true;
        }
        return false;
    }
}
