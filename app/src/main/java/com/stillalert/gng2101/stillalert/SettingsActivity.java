package com.stillalert.gng2101.stillalert;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
    public String idleTime;

    private static final String TAG = "SettingsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSoundSwitch = findViewById(R.id.switch_sound);
        mVibrationSwitch = findViewById(R.id.switch_vibration);
        mIdleTime = findViewById(R.id.time_editText);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String soundKey = preferences.getString("sound","true");
        String vibrateKey = preferences.getString("vibrate", "true");
        int idleTimeKey = preferences.getInt("idleTime", 30);

        mSoundSwitch.setChecked(Boolean.parseBoolean(soundKey));
        mVibrationSwitch.setChecked(Boolean.parseBoolean(vibrateKey));
        mIdleTime.setText(Integer.toString(idleTimeKey));
    }


    public void save(View view) {
        sound = mSoundSwitch.isChecked();
        vibrate = mVibrationSwitch.isChecked();
        idleTime = mIdleTime.getText().toString();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("sound", String.valueOf(sound));
        editor.putString("vibrate", String.valueOf(vibrate));
        editor.putInt("idleTime", Integer.parseInt(idleTime));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
