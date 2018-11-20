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

import java.util.Timer;

import static android.os.VibrationEffect.DEFAULT_AMPLITUDE;

public class Tracking extends AppCompatActivity implements SensorEventListener {

    private Sensor mSensor;
    private SensorManager mSensorManager;
    private float tempX, tempY, tempZ;
    private String flag;
    private int counterc = 0;
    private Context nContext;
    private boolean soundEnabled;
    private boolean vibrationEnabled;
    private int idleTime;


    private static final String TAG = "Tracking";

    public Tracking(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(Tracking.this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        nContext = context;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        soundEnabled = Boolean.parseBoolean(preferences.getString("sound","true"));
        vibrationEnabled = Boolean.parseBoolean(preferences.getString("vibrate", "true"));
        idleTime = preferences.getInt("idleTime", 30);
    }

    @Override
    public void onSensorChanged(final SensorEvent sensorEvent) {
        Timer timer = new Timer();

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
            counterc = 0;
        }
        tempX = sensorEvent.values[0];
        tempY = sensorEvent.values[1];
        tempZ = sensorEvent.values[2];
        counterc++;
        if (counterc > 10) {
            while (((Math.abs(tempX - sensorEvent.values[0])) < 0.1) || (Math.abs((tempY - sensorEvent.values[1])) < 0.1) || (Math.abs((tempZ - sensorEvent.values[2])) < 0.1)) {


                Log.d(TAG, "onSensorChanged: counter");
                Log.d(TAG, "onSensorChanged: " + idleTime);
                Log.d(TAG, "onSensorChanged: " + System.currentTimeMillis() / 1000);
                if ((System.currentTimeMillis() / 1000) - idleTime > 1) {
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

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //vibrator.vibrate(DEFAULT_AMPLITUDE);
    }
}
