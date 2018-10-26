package com.stillalert.gng2101.stillalert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void stopActivity(View view){
        Intent intent = new Intent(this, StopActivity.class);
        startActivity(intent);
    }

    public void settingsActivty(View view){
//        Intent intent = new Intent(this, SettingsActivity.class);
//        startActivity(intent);
    }
}
