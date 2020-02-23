package com.fyp.ble.ownerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.fyp.ble.ownerapp.Model.BeaconListItem;
import com.fyp.ble.ownerapp.Model.PathModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main3Activity extends AppCompatActivity {

    String maclist;
    Spinner dropdownStart;
    Spinner dropdownEnd;

    String startMAC;
    String endMAC;

    EditText editTextAngle;
    EditText editTextCost;

    ListView listView;
    PathAdapter pathAdapter;

    Button buttonAdd;
    Button buttonSubmit;

    String initialMac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        final RequestQueue queue = Volley.newRequestQueue(this);

        editTextAngle = findViewById(R.id.AngleText);
        editTextCost = findViewById(R.id.costText);
        listView = (ListView)findViewById(R.id.listView);
        final ArrayList<PathModel> pathModelArrayList = new ArrayList<>();

        pathAdapter = new PathAdapter(this,pathModelArrayList);
        listView.setAdapter(pathAdapter);

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonSubmit = findViewById(R.id.buttonSubmit);

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

        Type listType = new TypeToken<List<HashMap<String, String>>>(){}.getType();
        List<HashMap<String, String>> listOfMACs = gson.fromJson(maclist, listType);

        final ArrayList<String> macs = new ArrayList<>();
        final ArrayList<String> macs2 = new ArrayList<>();
        ArrayList<String> locs = new ArrayList<>();
        final ArrayList<String> locs2 = new ArrayList<>();

        for (HashMap<String,String> hashMap:listOfMACs){
            macs.add(hashMap.get("mac"));
            macs2.add(hashMap.get("mac"));
            locs.add(hashMap.get("location"));
            locs2.add(hashMap.get("location"));

            if (hashMap.get("location").equals("initial beacon")){
                initialMac = hashMap.get("mac");
            }

        }

        dropdownStart = findViewById(R.id.spinnerStart);
        dropdownEnd = findViewById(R.id.spinnerEnd);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, macs);
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, macs2);
        dropdownStart.setAdapter(adapter1);
        dropdownEnd.setAdapter(adapter2);

//        dropdownStart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                pathModel.setStartMac(dropdownStart.getSelectedItem().toString());
////                macs2.remove(position);
////                locs2.remove(position);
////                adapter2.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // your code here
//            }
//
//        });
//
//        dropdownEnd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                pathModel.setEndMac(dropdownEnd.getSelectedItem().toString());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // your code here
//            }
//
//        });



        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PathModel pathModel = new PathModel();
                pathModel.setStartMac(dropdownStart.getSelectedItem().toString());
                pathModel.setEndMac(dropdownEnd.getSelectedItem().toString());
                pathModel.setAngle(Integer.parseInt(editTextAngle.getText().toString()));
                pathModel.setCost(Integer.parseInt(editTextCost.getText().toString()));

                pathModelArrayList.add(pathModel);
                pathAdapter.notifyDataSetChanged();

                editTextCost.setText("");
                editTextAngle.setText("");

                for (PathModel p:pathModelArrayList){
                    Log.d("rush",p.getAngle()+"");
                    Log.d("rush",p.getCost()+"");
                    Log.d("rush",p.getStartMac()+"");
                    Log.d("rush",p.getEndMac()+"");
                }

            }
        });


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    final String URL = "http://192.168.8.101:8080/api/createRels";
                    final String IniURL = "http://192.168.8.101:8080/api/createInitialRels";
                    final JSONArray jsonArray = new JSONArray();
                    final JSONArray jsonArrayInitial = new JSONArray();

                    for (PathModel b:pathModelArrayList){
                        JSONObject jsonBody2 = new JSONObject();
                        jsonBody2.put("startMac", b.getStartMac());
                        jsonBody2.put("endMac", b.getEndMac());
                        jsonBody2.put("angle", b.getAngle());
                        jsonBody2.put("cost", b.getCost());

                        if (b.getStartMac().equals(initialMac)){
                            jsonArrayInitial.put(jsonBody2);
                        }else {
                            jsonArray.put(jsonBody2);
                        }

                    }


                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, IniURL, jsonArrayInitial, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            Toast.makeText(getApplicationContext(), "Successfully Added Initial Relationships", Toast.LENGTH_SHORT).show();

                            JsonArrayRequest jsonArrayRequest2 = new JsonArrayRequest(Request.Method.POST, URL, jsonArray, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {

                                    Toast.makeText(getApplicationContext(), "Successfully Added All Relationships", Toast.LENGTH_SHORT).show();

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Log.d("rush",error.getMessage()+" ");

                                }
                            });
                            queue.add(jsonArrayRequest2);


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Log.d("rush",error.getMessage()+" ");

                        }
                    });
                    queue.add(jsonArrayRequest);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });




    }
}
