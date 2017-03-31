package com.example.rajukumarsingh.bloodbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Donor_login extends AppCompatActivity {
    EditText email,password;
    Button log_in;
    Double d1,d2,d3,d4;
    RequestQueue requestQueue;
    String url="http://rahulraj47.coolpage.biz/donor_login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        log_in= (Button) findViewById(R.id.button9);
        email= (EditText) findViewById(R.id.editText);
        password= (EditText) findViewById(R.id.editText2);
        requestQueue= Volley.newRequestQueue(this);

        Bundle bundle=getIntent().getExtras();
        d1=bundle.getDouble("Latitude");
        d2=bundle.getDouble("Longitude");

        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        int f=0;
                        for (int i=0;i<jsonArray.length();i++)
                        {
                            try {
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                String email1=jsonObject.getString("Email");
                                String password1=jsonObject.getString("Password");
                                String u_name=email.getText().toString();
                                String p=password.getText().toString();
                                if(email1.equals(u_name)&&password1.equals(p))
                                    f=1;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if(f==1) {
                            Toast.makeText(Donor_login.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Donor_login.this,Bloodbank_list.class);
                            intent.putExtra("Latitude",d1);
                            intent.putExtra("Longitude",d2);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(Donor_login.this, "Wrong Username or Password.", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(Donor_login.this, ""+volleyError, Toast.LENGTH_SHORT).show();
                    }
                }
                );
                requestQueue.add(jsonArrayRequest);
            }
        });
    }
}
