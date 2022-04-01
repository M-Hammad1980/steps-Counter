package com.me.stepcounter;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener, StepListener {
    private TextView TvSteps;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = " ";
    private int numSteps;
    Button BtnStart;
    Button BtnStop, BtnReset;
    pl.droidsonroids.gif.GifImageView gifImageView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        TvSteps = (TextView) findViewById(R.id.tv_steps);
        gifImageView = findViewById(R.id.gif);
        BtnStart = (Button) findViewById(R.id.btn_start);
        BtnStop = (Button) findViewById(R.id.btn_stop);
        BtnReset = (Button) findViewById(R.id.btn_reset);
        BtnStart.setBackgroundColor(Color.DKGRAY);
        BtnStop.setBackgroundColor(Color.DKGRAY);
        BtnReset.setBackgroundColor(Color.rgb(255,170,120));

        gifImageView.setVisibility(View.INVISIBLE);

        BtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                gifImageView.setVisibility(View.VISIBLE);
                BtnStop.setBackgroundColor(Color.DKGRAY);
                BtnStart.setBackgroundColor(Color.rgb(0,170,172));

                sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

            }
        });


        BtnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                BtnStop.setBackgroundColor(Color.rgb(0,170,172));
                BtnStart.setBackgroundColor(Color.DKGRAY);
                gifImageView.setVisibility(View.INVISIBLE);
                TvSteps.setText(TEXT_NUM_STEPS + numSteps);
//                TvSteps.setText(numSteps);
                sensorManager.unregisterListener(MainActivity.this);

            }
        });

        BtnReset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                BtnStart.setBackgroundColor(Color.DKGRAY);
                BtnStop.setBackgroundColor(Color.DKGRAY);
                gifImageView.setVisibility(View.INVISIBLE);
                numSteps = 0;
                TvSteps.setText(TEXT_NUM_STEPS + numSteps);
//                TvSteps.setText(numSteps);
                sensorManager.unregisterListener(MainActivity.this);

            }
        });



    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        TvSteps.setText(TEXT_NUM_STEPS + numSteps);
    }

}