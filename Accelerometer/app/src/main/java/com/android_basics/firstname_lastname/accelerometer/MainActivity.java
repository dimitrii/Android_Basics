package com.android_basics.firstname_lastname.accelerometer;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener{

    private SensorManager sensorManager;

    TextView xAxis;
    TextView yAxis;
    TextView zAxis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xAxis = (TextView)findViewById(R.id.xAxisTextView);
        yAxis = (TextView)findViewById(R.id.yAxisTextView);
        zAxis = (TextView)findViewById(R.id.zAxisTextView);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            xAxis.setText("" + x);
            yAxis.setText("" + y);
            zAxis.setText("" + z);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
