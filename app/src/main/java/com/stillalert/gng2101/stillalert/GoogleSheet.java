package com.stillalert.gng2101.stillalert;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class GoogleSheet extends AppCompatActivity {
    private static final String TAG = "sheet";



    private void addItemToSheet(final String idleTime,Context context){
        Log.d(TAG, "addItemToSheet: Reached addItem: " + idleTime);
        StringRequest request = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbyiJFkPAjjjiGXLLFv5b2gZYAuBiGJp0W2X6ezbVvlz26VL9i5T/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

            @Override
            protected Map<String,String> getParams(){
                Log.d(TAG, "getParams: reached Map method");
                Map<String,String> params = new HashMap<>();

                params.put("action","addItem");
                params.put("idleTime",idleTime);

                return params;

            }

        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(retryPolicy);
        Log.d(TAG, "addItemToSheet: REEEEEEEACHED");
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);




    }

    public void onClick(String time,Context context){
        Log.d(TAG, "onClick: Reached on click");
        addItemToSheet(time,context);
    }
}
