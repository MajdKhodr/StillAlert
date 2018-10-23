package com.stillalert.gng2101.stillalert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop);
    }

    public void mainActivity(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
