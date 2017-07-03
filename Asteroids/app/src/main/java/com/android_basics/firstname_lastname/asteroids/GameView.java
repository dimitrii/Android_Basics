package com.android_basics.firstname_lastname.asteroids;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {

    GameThread thread;

    private SpaceShip spaceShip;

    private ArrayList<CanvasObject> backdropSpaceObjects;
    private ArrayList<CanvasObject> tempBackdropSpaceObjects;
    private ArrayList<CanvasObject> backdropSpaceObjectsToRemove;
    private ArrayList<CanvasObject> spaceObjects;
    private ArrayList<CanvasObject> spaceObjectsToRemove;
    private ArrayList<CanvasObject> tempSpaceObjects;

    private Random generator = new Random();
    private Bitmap resizedAsteroidBitmap;

    private SensorManager sensorManager;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);

        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);

        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (spaceShip == null)
            return;

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float rawX = event.values[0];
            float normalizedX = rawX * -2f;
            spaceShip.setXVelocity((int)normalizedX);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

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
        }, 0, 100);

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
        }, 0, 1000);

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
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            if(event.getX() > getWidth()/2) {
//                spaceShip.setXVelocity(10);
//            }
//            else {
//                spaceShip.setXVelocity(-10);
//            }
//        } else if (event.getAction() == MotionEvent.ACTION_UP) {
//            spaceShip.setXVelocity(0);
//        }
        return true;
    }

    @Override
    protected void onDraw(final Canvas canvas) {

        if(canvas == null)
            return;

        canvas.drawColor(Color.BLACK);

        // Draw Backdrop
        synchronized (backdropSpaceObjects) {
            tempBackdropSpaceObjects = new ArrayList<CanvasObject>(backdropSpaceObjects);
        }
        for (CanvasObject spaceObject : tempBackdropSpaceObjects) {
            spaceObject.drawBitmap(canvas);
            if (spaceObject.checkRemove(canvas)) {
                backdropSpaceObjectsToRemove.add(spaceObject);
            }
        }
        tempBackdropSpaceObjects.removeAll(backdropSpaceObjectsToRemove);
        backdropSpaceObjects = tempBackdropSpaceObjects;
        backdropSpaceObjectsToRemove.clear();

        // Draw spaceObject
        synchronized (spaceObjects) {
            tempSpaceObjects = new ArrayList<CanvasObject>(spaceObjects);
        }
        for (CanvasObject spaceObject : tempSpaceObjects) {
            spaceObject.drawBitmap(canvas);
            if (spaceObject.checkRemove(canvas)) {
                spaceObjectsToRemove.add(spaceObject);
            }
        }
        tempSpaceObjects.removeAll(spaceObjectsToRemove);
        spaceObjects = tempSpaceObjects;
        spaceObjectsToRemove.clear();

        // Spaceship
        spaceShip.drawBitmap(canvas);
    }
}
