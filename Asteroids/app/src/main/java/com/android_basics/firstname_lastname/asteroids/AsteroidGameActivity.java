package com.android_basics.firstname_lastname.asteroids;

import android.app.Activity;
import android.os.Bundle;

public class AsteroidGameActivity extends Activity {

    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(gameView = new GameView(this));
    }

    @Override
    public void onBackPressed() {
        // Don't Do anything
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
