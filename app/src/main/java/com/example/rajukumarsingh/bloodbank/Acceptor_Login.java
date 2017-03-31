package com.example.rajukumarsingh.bloodbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Acceptor_Login extends AppCompatActivity {
    EditText email, password;
    Button login;
    Double d1, d2;
    RequestQueue requestQueue;
    String url = "http://rahulraj47.coolpage.biz/Acceptor_login.php";
    String city,data;
    String url1="http://rahulraj47.coolpage.biz/donor2.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptor__login);
        email = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        login = (Button) findViewById(R.id.button9);
        requestQueue = Volley.newRequestQueue(this);

        Bundle bundle = getIntent().getExtras();
        d1 = bundle.getDouble("Latitude");
        d2 = bundle.getDouble("Longitude");

        SharedPreferences mPrefs = getSharedPreferences("IDvalue", 0);
        city = mPrefs.getString("c", "");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                data = s;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(Acceptor_Login.this, ""+volleyError, Toast.LENGTH_SHORT).show();
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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        int f = 0;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String reg_no1 = jsonObject.getString("Email");
                                String password1 = jsonObject.getString("Password");
                                String u_name = email.getText().toString();
                                String p = password.getText().toString();
                                if (reg_no1.equals(u_name) && password1.equals(p))
                                    f = 1;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (f == 1) {
                            Toast.makeText(Acceptor_Login.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Acceptor_Login.this, Acceptor_2.class);
                            intent.putExtra("Latitude", d1);
                            intent.putExtra("Longitude", d2);
                            intent.putExtra("JSON",data);
                            startActivity(intent);
                        } else
                            Toast.makeText(Acceptor_Login.this, "Wrong Username or Password.", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(Acceptor_Login.this, "" + volleyError, Toast.LENGTH_SHORT).show();
                    }
                }
                );
                requestQueue.add(jsonArrayRequest);
            }
        });

    }
}
