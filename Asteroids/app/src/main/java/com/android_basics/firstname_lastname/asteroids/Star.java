package com.android_basics.firstname_lastname.asteroids;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

public class Star extends CanvasObject {

    private float radius;
    private Paint paint;

    private final static float SMALL_R = 5f;
    private final static int SMALL_VEL = 5;
    private final static float LARGE_R = 10f;
    private final static int LARGE_VEL = 10;

    public Star(int x, int y) {
        this.x = x;
        this.y = y;

        Random generator = new Random();
        if (generator.nextFloat() > 0.5f) {
            this.yVelocity = SMALL_VEL;
            this.radius = SMALL_R;
            paint = new Paint();
            paint.setColor(Color.argb(150,255,255,255));
        } else {
            this.yVelocity = LARGE_VEL;
            this.radius = LARGE_R;
            paint = new Paint();
            paint.setColor(Color.argb(230,255,255,255));
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
