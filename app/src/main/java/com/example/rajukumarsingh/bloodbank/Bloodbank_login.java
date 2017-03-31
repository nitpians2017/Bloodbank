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

public class Bloodbank_login extends AppCompatActivity {
 EditText reg_no,password;
    Button login;
    RequestQueue requestQueue;
    String url="http://rahulraj47.coolpage.biz/blood_bank_login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloodbank_login);
        reg_no= (EditText) findViewById(R.id.editText);
        password= (EditText) findViewById(R.id.editText2);
        login= (Button) findViewById(R.id.button9);
        requestQueue= Volley.newRequestQueue(this);

        login.setOnClickListener(new View.OnClickListener() {
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
                                String reg_no1=jsonObject.getString("Reg_No");
                                String password1=jsonObject.getString("Contact");
                                String u_name=reg_no.getText().toString();
                                String p=password.getText().toString();
                                if(reg_no1.equals(u_name)&&password1.equals(p))
                                    f=1;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if(f==1) {
                            Toast.makeText(Bloodbank_login.this, "Login Successful!", Toast.LENGTH_SHORT).show();
//                            Intent intent=new Intent(Bloodbank_login.this,Bloodbank_list.class);
//
//                            startActivity(intent);
                        }
                        else
                            Toast.makeText(Bloodbank_login.this, "Wrong Username or Password.", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(Bloodbank_login.this, ""+volleyError, Toast.LENGTH_SHORT).show();
                    }
                }
                );
                requestQueue.add(jsonArrayRequest);
            }
        });

    }
}
