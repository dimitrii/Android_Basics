package com.android_basics.firstname_lastname.lifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mainText;

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
        mainText.append("onCreate\n");
    }

    protected void onStart() {
        super.onStart();
        mainText.append("onStart\n");
    }

    protected void onResume() {
        super.onResume();
        appendText("onResume");
    }

    protected void onPause() {
        super.onPause();
        appendText("onPause");
    }

    protected void onStop() {
        super.onStop();
        appendText("onStop");
    }

    protected void onRestart() {
        super.onRestart();
        appendText("onRestart");
    }

    protected void onDestroy() {
        super.onDestroy();
        appendText("onDestroy");
    }

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
