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

    private ArrayList<CanvasObject> backdropSpaceObjects;
    private ArrayList<CanvasObject> backdropSpaceObjectsToRemove;
    private ArrayList<CanvasObject> spaceObjects;
    private ArrayList<CanvasObject> spaceObjectsToRemove;

    private Random generator = new Random();
    private Bitmap resizedAsteroidBitmap;

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

        backdropSpaceObjects = new ArrayList<CanvasObject>();
        backdropSpaceObjectsToRemove = new ArrayList<CanvasObject>();
        spaceObjects = new ArrayList<CanvasObject>();
        spaceObjectsToRemove = new ArrayList<CanvasObject>();

        // Create Scene
        Bitmap spaceshipBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.spaceship);
        spaceShip = new SpaceShip(spaceshipBitmap, 50, getHeight() - spaceshipBitmap.getHeight());

        // Launch Stars
        Timer starTimer = new Timer();
        starTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Star star = new Star(generator.nextInt(getWidth()), 0);
                backdropSpaceObjects.add(star);
            }
        }, 0, 300);

        // Launch Asteroids
        resizedAsteroidBitmap = Util.getResizedBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.asteroid),
                200, 200);
        Timer asteroidTimer = new Timer();
        asteroidTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Asteroid asteroid = new Asteroid(resizedAsteroidBitmap, generator.nextInt(getWidth()), 0);
                spaceObjects.add(asteroid);
            }
        }, 0, 2000);

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
    protected void onDraw(final Canvas canvas) {

        if(canvas == null)
            return;

        canvas.drawColor(Color.BLACK);

        // Draw Backdrop
        ArrayList<CanvasObject> tempBackdropSpaceObjects = new ArrayList<CanvasObject>(backdropSpaceObjects);
        for (CanvasObject spaceObject : tempBackdropSpaceObjects) {
            spaceObject.drawBitmap(canvas);
            if (spaceObject.checkRemove(canvas)) {
                backdropSpaceObjectsToRemove.add(spaceObject);
            }
        }
        backdropSpaceObjects.removeAll(backdropSpaceObjectsToRemove);
        backdropSpaceObjectsToRemove.clear();

        // Draw spaceObject
        ArrayList<CanvasObject> tempSpaceObjects = new ArrayList<CanvasObject>(spaceObjects);
        for (CanvasObject spaceObject : tempSpaceObjects) {
            spaceObject.drawBitmap(canvas);
            if (spaceObject.checkRemove(canvas)) {
                spaceObjectsToRemove.add(spaceObject);
            }
        }
        spaceObjects.removeAll(spaceObjectsToRemove);
        spaceObjectsToRemove.clear();

        // Spaceship
        spaceShip.drawBitmap(canvas);
    }
}
