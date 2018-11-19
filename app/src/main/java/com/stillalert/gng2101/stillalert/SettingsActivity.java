package com.stillalert.gng2101.stillalert;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {
    public Switch mSoundSwitch;
    public Switch mVibrationSwitch;
    public EditText mIdleTime;
    public boolean vibrate;
    public boolean sound;
    public String time;

    private static final String TAG = "SettingsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSoundSwitch = findViewById(R.id.switch_sound);
        mSoundSwitch = findViewById(R.id.switch_vibration);
        mIdleTime = findViewById(R.id.time_editText);

        mIdleTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                time = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void save(View view) {
        sound = mSoundSwitch.isChecked();
        vibrate = mVibrationSwitch.isChecked();


        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Sound", time);
        intent.putExtra("Vibrate", time);
        intent.putExtra("Time", time);

        startActivity(intent);

        Log.d(TAG, "Settings activity Time = " + time);
    }


}
