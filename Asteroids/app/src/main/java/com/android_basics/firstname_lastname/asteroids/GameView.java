package com.android_basics.firstname_lastname.asteroids;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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

    private ArrayList<CanvasObject> spaceObjects;
    private ArrayList<Integer> spaceObjectsToDelete;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        // Create Scene
        spaceShip = new SpaceShip(BitmapFactory.decodeResource(getResources(), R.drawable.spaceship), 50, 50);

        spaceObjects = new ArrayList<CanvasObject>();
        spaceObjectsToDelete = new ArrayList<Integer>();

        // Create Stars
        for(int i =0; i < 100; i++) {
            Random generator = new Random();
            int starX = generator.nextInt(getWidth());
            int starY = generator.nextInt(getHeight());
            spaceObjects.add(new Star(starX, starY));
        }

        // Launch Astroids
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
                spaceObjects.add(asteroid);
            }
        }
        AsteroidTimer astroidTimerTask = new AsteroidTimer(resizedAsteroidBitmap);
        t.scheduleAtFixedRate(astroidTimerTask, 0, 2000);

        // Start Thread
        thread = new GameThread(getHolder(),this);
        thread.setRunning(true);
        thread.start();
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

        // Draw Space Objects
        synchronized (spaceObjects) {
            for (CanvasObject spaceObject : spaceObjects) {
                spaceObject.drawBitmap(canvas);
                if (spaceObject.getY() > getHeight()) {
                    Integer integer = spaceObjects.indexOf(spaceObject);
                    spaceObjectsToDelete.add(integer);
                }
            }
        }
        for(Integer i: spaceObjectsToDelete) {
            spaceObjects.remove(i.intValue());
        }
        spaceObjectsToDelete.clear();
    }
}
