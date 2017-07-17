package com.android_basics.firstname_lastname.asteroids;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Worm {

    private int n = 100;
    private boolean tail[][] = new boolean[n][n];

    public void draw(Canvas canvas) {

        // draw tail
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        int squareWidth = canvasWidth / n;
        int squareHeight = canvasHeight / n;

        Paint squarePaint = new Paint();
        squarePaint.setColor(Color.WHITE);

        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                boolean drawSquare = tail[i][j];
                if (drawSquare) {
                    int squareTopX = i * squareWidth;
                    int squareTopY = j * squareHeight;
                    int sqareBottomX = squareTopX + squareWidth;
                    int sqareBottomY = squareTopY + squareHeight;
                    canvas.drawRect(squareTopX, squareTopX, sqareBottomX, sqareBottomY,squarePaint);
                }
            }
        }

    }
}
