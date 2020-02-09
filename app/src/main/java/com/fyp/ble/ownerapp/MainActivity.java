package com.fyp.ble.ownerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fyp.ble.ownerapp.Model.IniBeacon;
import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity {

    Button next;
    EditText description;
    EditText mac;

    String descriptionStr;
    String macStr;

    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RequestQueue queue = Volley.newRequestQueue(this);

        gson = new Gson();

        next = (Button)findViewById(R.id.buttonNext);

        description = (EditText)findViewById(R.id.editText);
        mac = (EditText)findViewById(R.id.editText2);


//        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
//        MainActivity.this.startActivity(intent);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("rush","inside");
                Log.d("rush",description.getText().toString());
                if (!mac.getText().toString().isEmpty() && !description.getText().toString().isEmpty()) {
                    Log.d("rush","inside");
                    final IniBeacon iniBeacon = new IniBeacon(description.getText().toString(), mac.getText().toString());


                    String URL = "http://192.168.8.101:8080/api/createIniBeacon?description="+iniBeacon.getDescription()+"&MAC="+iniBeacon.getMAC();

//                    String URI = String.format(URL)
//                        JSONObject jsonBody = new JSONObject(gson.toJson(iniBeacon));
                    final String requestBody = gson.toJson(iniBeacon);



// Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Display the first 500 characters of the response string.
                                    Log.d("rush",response);
                                    Toast.makeText(MainActivity.this, "Created Initial Beacon", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                                    intent.putExtra("initial mac",iniBeacon.getMAC());
                                    MainActivity.this.startActivity(intent);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("rush",""+error.getMessage());
                        }
                    });

// Add the request to the RequestQueue.
                    queue.add(stringRequest);



                }
            }

        });
}
}
