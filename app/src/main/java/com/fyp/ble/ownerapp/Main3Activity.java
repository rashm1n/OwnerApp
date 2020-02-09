package com.fyp.ble.ownerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

public class Main3Activity extends AppCompatActivity {

    String maclist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Gson gson = new Gson();


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                maclist= null;
            } else {
                maclist= extras.getString("mac list");
            }
        } else {
            maclist= (String) savedInstanceState.getSerializable("mac list");
        }

        try {
            JSONArray jsonArray = new JSONArray(maclist);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("rush",maclist);

    }
}
