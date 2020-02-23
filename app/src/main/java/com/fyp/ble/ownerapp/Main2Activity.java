package com.fyp.ble.ownerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fyp.ble.ownerapp.Model.BeaconListItem;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {

    ListView listView;
    Button button;
    Button submitButton;
    private BeaconAdapter beaconAdapter;
    EditText loc;
    EditText des;
    EditText MC;

    String iniMAC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                iniMAC= null;
            } else {
                iniMAC= extras.getString("initial mac");
            }
        } else {
            iniMAC= (String) savedInstanceState.getSerializable("initial mac");
        }


        final RequestQueue queue = Volley.newRequestQueue(this);

        loc = (EditText)findViewById(R.id.locationText);
        des = (EditText)findViewById(R.id.descriptionText);
        MC = (EditText)findViewById(R.id.MACtext);

        button = (Button)findViewById(R.id.button);
        submitButton = (Button)findViewById(R.id.button2);
        listView = (ListView)findViewById(R.id.listView);
        final ArrayList<BeaconListItem> beaconListItems = new ArrayList<>();

        beaconAdapter = new BeaconAdapter(this,beaconListItems);
        listView.setAdapter(beaconAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!loc.getText().toString().isEmpty() && !des.getText().toString().isEmpty() && !MC.getText().toString().isEmpty()){
                BeaconListItem beaconListItem = new BeaconListItem(loc.getText().toString(),des.getText().toString(),MC.getText().toString());
                beaconListItems.add(beaconListItem);
                beaconAdapter.notifyDataSetChanged();
                loc.setText("");
                des.setText("");
                MC.setText("");
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String URL = "http://192.168.8.101:8080/api/createBeacons";
                    JSONArray jsonArray = new JSONArray();
                    final JSONArray jsonArrayToBeSent = new JSONArray();
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("description", "initial beacon");
                    jsonBody.put("mac", iniMAC);
                    jsonBody.put("location", "initial beacon");
                    jsonArrayToBeSent.put(jsonBody);

                    for (BeaconListItem b:beaconListItems){
                        JSONObject jsonBody2 = new JSONObject();
                        jsonBody2.put("description", b.getDescription());
                        jsonBody2.put("mac", b.getMAC());
                        jsonBody2.put("location", b.getLocation());
                        jsonArray.put(jsonBody2);
                        jsonArrayToBeSent.put(jsonBody2);
                    }


                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL, jsonArray, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            Toast.makeText(getApplicationContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Main2Activity.this,Main3Activity.class);
                            intent.putExtra("mac list",jsonArrayToBeSent.toString());
                            Main2Activity.this.startActivity(intent);

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

