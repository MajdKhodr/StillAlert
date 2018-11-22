package com.stillalert.gng2101.stillalert;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.sql.Timestamp;

public class Tracking extends AppCompatActivity implements SensorEventListener {

    private Sensor mSensor;
    private SensorManager mSensorManager;
    private float calibrateX, calibrateY, calibrateZ;
    private Context nContext;
    private boolean soundEnabled;
    private boolean vibrationEnabled;
    private int idleTime;
    private boolean flag;
    float tempX, tempY, tempZ;
    long timestamp;
    private boolean stopped;


    private static final String TAG = "Tracking";

    public Tracking(Context context) {

        // Setup sensors
        nContext = context;
        mSensorManager = (SensorManager) nContext.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(Tracking.this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Load settings
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        soundEnabled = Boolean.parseBoolean(preferences.getString("sound", "true"));
        vibrationEnabled = Boolean.parseBoolean(preferences.getString("vibrate", "true"));
        idleTime = preferences.getInt("idleTime", 30);
        idleTime *= 1000;

        // Load calibration settings
        calibrateX = preferences.getFloat("calibrateX",0);
        calibrateY = preferences.getFloat("calibrateY",0);
        calibrateZ = preferences.getFloat("calibrateZ",0);

        tempX = 0;
        tempY = 0;
        tempZ = 0;

        //Start off
        flag = false;
        stopped = false;
        timestamp = 0;

    }

    @Override
    public void onSensorChanged(final SensorEvent sensorEvent) {
        if(!flag){
            return;
        }

        if(Math.abs(tempX - sensorEvent.values[0]) - calibrateX < 0.5 && Math.abs(tempY - sensorEvent.values[1]) - calibrateY < 0.5
                && Math.abs(tempZ - sensorEvent.values[2]) - calibrateZ < 0.5 ){
            Log.d("Sensor", "Still " + tempX);

            // If first stopped indication store time and set stopped to true
            if(!stopped){
                timestamp = System.currentTimeMillis();
                stopped = true;
            }

            // If has been stopped for more than idle time play sound
            if(System.currentTimeMillis() - timestamp > idleTime  && stopped){
                playSound();
                stopped = false;
            }


        }else{
            // Movement detected reset everything
            Log.d("Sensor", "Moving " + tempX);
            stopped = false;
            timestamp = 0;

        }





        // For calibration
        tempX = sensorEvent.values[0];
        tempY = sensorEvent.values[1];
        tempZ = sensorEvent.values[2];



    }


    @Override
    public void onAccuracyChanged(android.hardware.Sensor sensor, int accuracy) {
    }

    private void playSound() {
        final MediaPlayer mp = MediaPlayer.create(nContext, R.raw.sound);
        mp.start();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //vibrator.vibrate(DEFAULT_AMPLITUDE);
    }

    public void startTracking() {
        flag = true;
    }

    public void stopTracking() {
        flag = false;
    }

    public float getCalibrateX() {
        return tempX;
    }

    public float getCalibrateY() {
        return tempY;
    }

    public float getCalibrateZ() {
        return tempZ;
    }
}
