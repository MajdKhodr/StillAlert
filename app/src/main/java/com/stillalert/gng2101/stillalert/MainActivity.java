package com.stillalert.gng2101.stillalert;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private FloatingActionButton mButton;
    private TextView mTextView;
    private ImageView mSetting;
    private Sensor mSensor;
    private SensorManager mSensorManager;
    private float tempX, tempY, tempZ;
    private String flag;
    private int counter;
    private Long time;


    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flag = "OFF";

        time = System.currentTimeMillis() / 1000;

        mTextView = findViewById(R.id.button_start);

        mSetting = findViewById(R.id.imageview_setting);

        mButton = findViewById(R.id.floatingActionButton_start);


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mSensorManager.registerListener(MainActivity.this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onResume() {
        super.onResume();

        counter = 0;

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view instanceof FloatingActionButton) {
                    if ((mTextView.getText().toString()).equals("Start")) {
                        view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
                        mTextView.setText("Stop");
                        mSetting.setVisibility(View.INVISIBLE);
                        flag = "ON";

                    } else {
                        view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#008000")));
                        mTextView.setText("Start");
                        mSetting.setVisibility(View.VISIBLE);
                        flag = "OFF";


                    }
                }
            }
        });

    }

    public void settingsActivity(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSensorChanged(final SensorEvent sensorEvent) {
        if (flag.equals("OFF")) {
            return;
        }
        tempX = 0;
        tempY = 0;
        tempZ = 0;

        if (Math.abs(tempX - sensorEvent.values[0]) > 0.1 || tempY - sensorEvent.values[1] > 0.1 || tempZ - sensorEvent.values[2] > 0.1) {
            Log.d(TAG, "onSensorChange X: " + sensorEvent.values[0] + " Y:" + sensorEvent.values[1] + " Z:" + sensorEvent.values[2]);
            counter = 0;
        } else {
            tempX = sensorEvent.values[0];
            tempY = sensorEvent.values[1];
            tempZ = sensorEvent.values[2];
            counter++;
            if (counter > 10) {
                Log.d(TAG, "onSensorChanged: counter");
                Log.d(TAG, "onSensorChanged: " + time);
                Log.d(TAG, "onSensorChanged: " + System.currentTimeMillis()/1000);
                if((System.currentTimeMillis()/1000) - time > 1){
                    playSound();
                    time = System.currentTimeMillis()/1000;
                }
            }

        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    private void playSound() {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.sound);
        mp.start();
    }
}
