package com.android_basics.firstname_lastname.asteroids;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Star extends CanvasObject {

    private static final float R = 2.0f;
    private Paint paint;

    public Star(int x, int y) {
        this.x = x;
        this.y = y;

        this.paint = new Paint();
        this.paint.setColor(Color.WHITE);
    }

    @Override
    public void drawBitmap(Canvas canvas) {
        canvas.drawCircle((float)this.x, (float)this.y, Star.R, paint);
    }
}
