package com.android_basics.firstname_lastname.lifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainText = (TextView)findViewById(R.id.mainText);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("callbacks")) {
                String allPreviousLifecycleCallbacks = savedInstanceState
                        .getString("callbacks");
                mainText.setText(allPreviousLifecycleCallbacks);
            }
        }
        appendText("onCreate");
    }

    /**
     * Called when the activity is becoming visible to the user.
     *
     * Followed by onResume() if the activity comes to the foreground, or onStop() if it becomes
     * hidden.
     */
    @Override
    protected void onStart() {
        super.onStart();
        appendText("onStart");
    }
    /**
     * Called when the activity will start interacting with the user. At this point your activity
     * is at the top of the activity stack, with user input going to it.
     *
     * Always followed by onPause().
     */
    @Override
    protected void onResume() {
        super.onResume();
        appendText("onResume");
    }
    /**
     * Called when the system is about to start resuming a previous activity. This is typically
     * used to commit unsaved changes to persistent data, stop animations and other things that may
     * be consuming CPU, etc. Implementations of this method must be very quick because the next
     * activity will not be resumed until this method returns.
     *
     * Followed by either onResume() if the activity returns back to the front, or onStop() if it
     * becomes invisible to the user.
     */
    @Override
    protected void onPause() {
        super.onPause();
        appendText("onPause");
    }
    /**
     * Called when the activity is no longer visible to the user, because another activity has been
     * resumed and is covering this one. This may happen either because a new activity is being
     * started, an existing one is being brought in front of this one, or this one is being
     * destroyed.
     *
     * Followed by either onRestart() if this activity is coming back to interact with the user, or
     * onDestroy() if this activity is going away.
     */
    @Override
    protected void onStop() {
        super.onStop();
        appendText("onStop");
    }
    /**
     * Called after your activity has been stopped, prior to it being started again.
     *
     * Always followed by onStart()
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        appendText("onRestart");
    }
    /**
     * The final call you receive before your activity is destroyed. This can happen either because
     * the activity is finishing (someone called finish() on it, or because the system is
     * temporarily destroying this instance of the activity to save space. You can distinguish
     * between these two scenarios with the isFinishing() method.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        appendText("onDestroy");
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        appendText("onSaveInstanceState");
        String lifecycleDisplayTextViewContents = mainText.getText().toString();
        outState.putString("callbacks", lifecycleDisplayTextViewContents);
    }
    void appendText(String text) {
        mainText.append(text + "\n");
    }
}
