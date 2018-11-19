package com.stillalert.gng2101.stillalert;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class Tracking implements SensorEventListener {

    private Sensor mSensor;
    private SensorManager mSensorManager;
    private float tempX, tempY, tempZ;
    private String flag;
    private int counterc = 0;
    private static final String TAG = "Tracking";
    private int time = 0;
    private Context nContext;


    public Tracking(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(Tracking.this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        nContext = context;
    }

    @Override
    public void onSensorChanged(final SensorEvent sensorEvent) {
        if (flag.equals("OFF")) {
            return;
        }
        tempX = 0;
        tempY = 0;
        tempZ = 0;

        if (((Math.abs(tempX - sensorEvent.values[0])) < 0.1) || (Math.abs((tempY - sensorEvent.values[1])) < 0.1) || (Math.abs((tempZ - sensorEvent.values[2])) < 0.1)) {
            Log.d(TAG, "onSensorChange X: " + sensorEvent.values[0] + " Y:" + sensorEvent.values[1] + " Z:" + sensorEvent.values[2]);
            counterc++;
        }else{
            counterc =0;
        }
        tempX = sensorEvent.values[0];
        tempY = sensorEvent.values[1];
        tempZ = sensorEvent.values[2];
        counterc++;
        if (counterc > 10) {
            while (((Math.abs(tempX - sensorEvent.values[0])) < 0.1) || (Math.abs((tempY - sensorEvent.values[1])) < 0.1) || (Math.abs((tempZ - sensorEvent.values[2])) < 0.1)) {


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
        final MediaPlayer mp = MediaPlayer.create(nContext, R.raw.sound);
        mp.start();
    }
}
