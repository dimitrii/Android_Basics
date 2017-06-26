package com.android_basics.firstname_lastname.asteroids;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    GameThread thread;

    private SpaceShip spaceShip;

    private ArrayList<Asteroid> astroids;
    private ArrayList<Integer> astroidsToDelete;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(),this);
        setFocusable(true);

        // Create Scene
        spaceShip = new SpaceShip(BitmapFactory.decodeResource(getResources(), R.drawable.spaceship), 50, 50);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        thread.setRunning(true);
        thread.start();

        astroids = new ArrayList<Asteroid>();
        astroidsToDelete = new ArrayList<Integer>();

        Bitmap resizedAsteroidBitmap = Util.getResizedBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.asteroid),
                200, 200);

        Timer t = new Timer();

        class AsteroidTimer extends TimerTask {
            private Bitmap asteroidBitmap;
            AsteroidTimer(Bitmap bitmap) {
                super();
                this.asteroidBitmap = bitmap;
            }
            public void run() {
                Random generator = new Random();
                int startingXPosition = generator.nextInt(getWidth());
                Asteroid asteroid = new Asteroid(asteroidBitmap, startingXPosition, 0);
                astroids.add(asteroid);
            }
        }
        AsteroidTimer astroidTimerTask = new AsteroidTimer(resizedAsteroidBitmap);
        t.scheduleAtFixedRate(astroidTimerTask, 0, 2000);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if(event.getX() > getWidth()/2) {
                spaceShip.setXVelocity(10);
            }
            else {
                spaceShip.setXVelocity(-10);
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            spaceShip.setXVelocity(0);
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.BLACK);
        spaceShip.drawBitmap(canvas);

        // Draw Astroids
        synchronized (astroids) {
            for (Asteroid asteroid : astroids) {
                asteroid.drawBitmap(canvas);
                if (asteroid.getY() > getHeight()) {
                    Integer integer = astroids.indexOf(asteroid);
                    astroidsToDelete.add(integer);
                }
            }
        }
        for(Integer i: astroidsToDelete) {
            astroids.remove(i.intValue());
        }
        astroidsToDelete.clear();
    }
}
