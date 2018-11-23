package com.stillalert.gng2101.stillalert;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    private Switch mSoundSwitch;
    private Switch mVibrationSwitch;
    private EditText mIdleTime;
    private SeekBar mSeekBar;
    private boolean vibrate;
    private boolean sound;
    private String idleTime;
    private int seekBarIndex;
    private double sensitivity;
    private Button mCalibrate;
    private float calibrateX, calibrateY, calibrateZ;
    private Context context;
    private Tracking tracking;

    private final double[] listOfThresholds = {0.6, 0.55, 0.5, 0.45, 0.4};


    private static final String TAG = "SettingsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Setup UI
        mSoundSwitch = findViewById(R.id.switch_sound);
        mVibrationSwitch = findViewById(R.id.switch_vibration);
        mIdleTime = findViewById(R.id.time_editText);
        mSeekBar = findViewById(R.id.seekBar_threshhold);
        mSeekBar.setMax(4);
        mCalibrate = findViewById(R.id.calibrate);

        // Load settings
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String soundKey = preferences.getString("sound", "true");
        String vibrateKey = preferences.getString("vibrate", "true");
        int idleTimeKey = preferences.getInt("idleTime", 30);
        int seekBarIndexKey = preferences.getInt("seekBarIndex", 0);

        // Set settings
        mSoundSwitch.setChecked(Boolean.parseBoolean(soundKey));
        mVibrationSwitch.setChecked(Boolean.parseBoolean(vibrateKey));
        mIdleTime.setText(Integer.toString(idleTimeKey));
        mSeekBar.setProgress(seekBarIndexKey);

        // Save context
        context = this;

        mCalibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tracking = new Tracking(context);

                Toast.makeText(context, "Will start calibration in 2 seconds, place phone on table", Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        tracking.startTracking();
                    }
                }, 2000);


                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Toast.makeText(context, "Calibrating...", Toast.LENGTH_SHORT).show();
                        tracking.stopTracking();
                    }
                }, 2000);


                calibrateX = tracking.getCalibrateX();
                calibrateY = tracking.getCalibrateY();
                calibrateZ = tracking.getCalibrateZ();
            }
        });
    }


    public void save(View view) {

        // Get UI state
        sound = mSoundSwitch.isChecked();
        vibrate = mVibrationSwitch.isChecked();
        idleTime = mIdleTime.getText().toString();
        seekBarIndex = mSeekBar.getProgress();
        sensitivity = listOfThresholds[mSeekBar.getProgress()];

        // Save settings
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("sound", String.valueOf(sound));
        editor.putString("vibrate", String.valueOf(vibrate));
        editor.putInt("idleTime", Integer.parseInt(idleTime));
        editor.putInt("seekBarIndex", seekBarIndex);
        editor.putFloat("sensitivity", (float)sensitivity);
        editor.putFloat("calibrateX", calibrateX);
        editor.putFloat("calibrateY", calibrateY);
        editor.putFloat("calibrateZ", calibrateZ);
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
