package com.example.rajukumarsingh.bloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class Donor_login extends AppCompatActivity {
    EditText email, password;
    Button log_in, sign_up;
    Double d1, d2, d3, d4;
    RequestQueue requestQueue;
    String url=Constants.DONOR_LOGIN;
    RadioGroup radioGroup;
    RadioButton radioButton,radioButton1;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main4);
        log_in = (Button) findViewById(R.id.button9);
        sign_up = (Button) findViewById(R.id.signup);
        email = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        requestQueue = Volley.newRequestQueue(this);

        radioButton = (RadioButton) findViewById(R.id.radioButton3);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton4);
        radioButton1.setChecked(true);

//        Bundle bundle = getIntent().getExtras();
//        d1 = bundle.getDouble("Latitude");
//        d2 = bundle.getDouble("Longitude");

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Donor_login.this, DonorSignupActivity.class);
                intent.putExtra("email", email.getText().toString());
                startActivity(intent);

            }
        });

        findViewById(R.id.emergency).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNextActivity();
            }
        });

        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = new ProgressDialog(Donor_login.this);
                progressDialog.setMessage(getResources().getString(R.string.sign_in_loading));
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        int f = 0;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String email1 = jsonObject.getString("Email");
                                String password1 = jsonObject.getString("Password");
                                String u_name = email.getText().toString();
                                String p = password.getText().toString();
                                if (email1.equals(u_name) && password1.equals(p))
                                    f = 1;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        progressDialog.dismiss();
                        if (f == 1) {
                            Toast.makeText(Donor_login.this, R.string.Loginsuccess, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Donor_login.this, DashboardActivity.class);
                            startActivity(intent);
                        } else
                            Toast.makeText(Donor_login.this,R.string.LoginError, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(Donor_login.this, "" + volleyError, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                );
                requestQueue.add(jsonArrayRequest);
            }
        });
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Configuration config = new Configuration();
                Locale locale = new Locale("hi");
                config.locale = locale;
                getResources().updateConfiguration(config,null);
            }
        });

        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Configuration config = new Configuration();
                Locale locale = new Locale("en");
                config.locale = locale;
                getResources().updateConfiguration(config,null);
            }
        });
    }



    private void startNextActivity(){
        new com.example.rajukumarsingh.bloodbank.location.LocationCaptureTask(this){
            @Override
            public void afterLocationCapture(Location location) {
                if(location != null){
                    d1 = location.getLatitude();
                    d2 = location.getLongitude();

                    Intent i =new Intent(Donor_login.this,Bloodbank_list.class);
                    i.putExtra("Latitude",d1);
                    i.putExtra("Longitude",d2);
                    i.putExtra("src","dashboard");

                    String s3 = Double.toString(d1);
                    String s4 = Double.toString(d2);

                    SharedPreferences mPrefs = getSharedPreferences("IDvalue", 0);
                    SharedPreferences.Editor editor = mPrefs.edit();
                    editor.putString("lo", s3);
                    editor.putString("la", s4);

                    editor.commit();
                    startActivity(i);
                } else{
                    Toast.makeText(Donor_login.this,R.string.LocationError,Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
}
