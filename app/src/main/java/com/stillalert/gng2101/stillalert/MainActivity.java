package com.stillalert.gng2101.stillalert;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton mButton;
    TextView mTextView;
    ImageView mSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.button_start);

        mSetting = findViewById(R.id.imageview_setting);

        mButton = findViewById(R.id.floatingActionButton_start);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view instanceof FloatingActionButton){
                    if((mTextView.getText().toString()).equals("Start")) {
                        view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
                        mTextView.setText("Stop");
                        mSetting.setVisibility(View.INVISIBLE);

                    }else{
                        view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#008000")));
                        mTextView.setText("Start");
                        mSetting.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }


    public void settingsActivty(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
