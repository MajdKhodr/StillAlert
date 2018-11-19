package com.stillalert.gng2101.stillalert;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class Tracking extends AppCompatActivity implements SensorEventListener {

    private Sensor mSensor;
    private SensorManager mSensorManager;
    private float tempX, tempY, tempZ;
    private String flag;
    private int counter;
    private static final String TAG = "Tracking";
    private int time = 0;


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
                Log.d(TAG, "onSensorChanged: " + System.currentTimeMillis() / 1000);
                if ((System.currentTimeMillis() / 1000) - time > 1) {
                    playSound();
                    //time = System.currentTimeMillis()/1000;
                }
            }

        }
    }


    @Override
    public void onAccuracyChanged(android.hardware.Sensor sensor, int accuracy) {

    }

    private void playSound() {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.sound);
        mp.start();
    }
}
