package com.example.rajukumarsingh.bloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Acceptor_2 extends AppCompatActivity {
    Button button,button2;
    Double d1,d2;
    String JSON;
    private ProgressDialog dialog;

    RequestQueue requestQueue;
    String url = Constants.ACCEPTOR_LOGIN_URL;
    String city,data;
    String url1=Constants.DONOR_CITY_SEARCH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptor_2);
        button = (Button) findViewById(R.id.button3);
        button2 = (Button) findViewById(R.id.button5);



        Bundle bundle = getIntent().getExtras();
        d1 = bundle.getDouble("Latitude");
        d2 = bundle.getDouble("Longitude");
//        JSON = bundle.getString("JSON");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Acceptor_2.this,Donor_list.class);
                intent.putExtra("Latitude",d1);
                intent.putExtra("Longitude",d2);
                intent.putExtra("JSON",JSON);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Acceptor_2.this,Bloodbank_list.class);
                intent.putExtra("Latitude",d1);
                intent.putExtra("Longitude",d2);
                startActivity(intent);
            }
        });

        requestQueue = Volley.newRequestQueue(this);

        SharedPreferences mPrefs = getSharedPreferences("IDvalue", 0);
        city = mPrefs.getString("c", "");

        dialog = new ProgressDialog(this);
        dialog.setMessage(this.getResources().getString(R.string.Fetchinglocation));
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("JSON Response: ",s);
                JSON = s;
                if (dialog!=null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(Acceptor_2.this, ""+volleyError, Toast.LENGTH_SHORT).show();

                if (dialog!=null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("City", city);
                return params;
            }
        };
        requestQueue.add(stringRequest);

//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray jsonArray) {
//                        int f = 0;
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            try {
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                String reg_no1 = jsonObject.getString("Email");
//                                String password1 = jsonObject.getString("Password");
//                                String u_name = email.getText().toString();
//                                String p = password.getText().toString();
//                                if (reg_no1.equals(u_name) && password1.equals(p))
//                                    f = 1;
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        if (f == 1) {
//                            Toast.makeText(Acceptor_2.this, getResources().getString(R.string.Loginsuccess), Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(Acceptor_2.this, Acceptor_2.class);
//                            intent.putExtra("Latitude", d1);
//                            intent.putExtra("Longitude", d2);
//                            intent.putExtra("JSON",data);
//                            startActivity(intent);
//                        } else
//                            Toast.makeText(Acceptor_2.this, getResources().getString(R.string.LoginError), Toast.LENGTH_SHORT).show();
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        Toast.makeText(Acceptor_2.this, "" + volleyError, Toast.LENGTH_SHORT).show();
//                    }
//                }
//                );
//                requestQueue.add(jsonArrayRequest);
//            }
//        });

    }
}
