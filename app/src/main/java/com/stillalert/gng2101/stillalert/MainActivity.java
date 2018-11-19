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

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mButton;
    private TextView mTextView;
    private TextView mDisplayTime;
    private ImageView mSetting;
    private Sensor mSensor;
    private SensorManager mSensorManager;
    private float tempX, tempY, tempZ;
    private String flag;
    private int counter;
    private int time = 0;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flag = "OFF";

        mTextView = findViewById(R.id.button_start);

        mSetting = findViewById(R.id.imageview_setting);

        mButton = findViewById(R.id.floatingActionButton_start);

        mDisplayTime = findViewById(R.id.textview_time);

        Log.d(TAG, "value is " + getIntent().getSerializableExtra("Time"));

        if (getIntent().getSerializableExtra("Time") != null) {
            time = (int) getIntent().getSerializableExtra("Time");
        }

        Log.d(TAG, "MainActivity Time = " + time);

        mDisplayTime.setText(Integer.toString(time));
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
}
