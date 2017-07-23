package com.android_basics.firstname_lastname.asteroids;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

    private Timer starsTimer;
    private Timer asteroidTimer;

    private Random generator = new Random();
    private Bitmap resizedAsteroidBitmap;

    private SensorManager sensorManager;

    private boolean showLines;
    private final Paint linePaint;

    private boolean gameOver;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);

        linePaint = new Paint();
        linePaint.setColor(Color.WHITE);

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
            float rawY = event.values[1];
            float normalizedX = rawX * -2f;
            float normalizedY = rawY * 2f - 7f;
            spaceShip.setXVelocity((int)normalizedX);
            spaceShip.setYVelocity((int)normalizedY);
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

        // Launch Asteroids
        resizedAsteroidBitmap = Util.getResizedBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.asteroid),
                200, 200);

        // Launch Stars
        starsTimer = new Timer();
        starsTimer.scheduleAtFixedRate(new CreateStarsTimerTask(), 0, 100);

        asteroidTimer = new Timer();
        asteroidTimer.scheduleAtFixedRate(new CreateAstertoidsTimerTask(), 0, 1000);

        // Start Thread
        startThread();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopThread();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            showLines = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            showLines = false;
        }
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
        // Draw Line
        if (showLines) {
            CanvasObject lastSpaceObject = null;
            for (CanvasObject spaceObject : tempBackdropSpaceObjects) {
                if (lastSpaceObject != null) {
                    canvas.drawLine(
                            lastSpaceObject.getX(),
                            lastSpaceObject.getY(),
                            spaceObject.getX(),
                            spaceObject.getY(), linePaint);
                }
                lastSpaceObject = spaceObject;
            }
        }
        // Draw Stars
        for (CanvasObject spaceObject : tempBackdropSpaceObjects) {
            spaceObject.drawBitmap(canvas);
            if (spaceObject.checkRemove(canvas)) {
                backdropSpaceObjectsToRemove.add(spaceObject);
            }
        }
        tempBackdropSpaceObjects.removeAll(backdropSpaceObjectsToRemove);
        backdropSpaceObjects = tempBackdropSpaceObjects;
        backdropSpaceObjectsToRemove.clear();

        // Spaceship
        spaceShip.drawBitmap(canvas);

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

    }

    public void stop() {
        stopTimers();
        stopThread();
    }

    public void start () {
        startTimers();
        stopThread();
    }

    public void stopThread() {
        boolean retry = true;
        while(retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    public void startThread() {
        thread = new GameThread(getHolder(),this);
        thread.setRunning(true);
        thread.start();
    }

    public void stopTimers() {
        starsTimer.cancel();
        asteroidTimer.cancel();
    }

    public void startTimers() {

        // Launch Stars
        starsTimer = new Timer();
        starsTimer.scheduleAtFixedRate(new CreateStarsTimerTask(), 0, 100);

        asteroidTimer = new Timer();
        asteroidTimer.scheduleAtFixedRate(new CreateAstertoidsTimerTask(), 0, 1000);
    }

    private class CreateStarsTimerTask extends TimerTask {
        @Override
        public void run() {
            Star star = new Star(generator.nextInt(getWidth()), 0);
            backdropSpaceObjects.add(star);
        }
    }

    private class CreateAstertoidsTimerTask extends TimerTask {
        @Override
        public void run() {
            Asteroid asteroid = new Asteroid(resizedAsteroidBitmap, generator.nextInt(getWidth()), 0);
            spaceObjects.add(asteroid);
        }
    }
}
