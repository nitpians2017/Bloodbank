package com.example.rajukumarsingh.bloodbank;

import android.content.Intent;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Donor_signup extends AppCompatActivity {
    EditText name, dob, address, city, district, state, pin;
    String Name, Dob, Address, City, District, State, Pin;
    Button date, next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);


        name = (EditText) findViewById(R.id.editText3);
        dob = (EditText) findViewById(R.id.editText4);
        address = (EditText) findViewById(R.id.editText5);
        city = (EditText) findViewById(R.id.editText6);
        district = (EditText) findViewById(R.id.editText7);
        state = (EditText) findViewById(R.id.editText8);
        pin = (EditText) findViewById(R.id.editText9);
        date = (Button) findViewById(R.id.button10);
        next = (Button) findViewById(R.id.button12);


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = name.getText().toString();
                Dob = dob.getText().toString();
                Address = address.getText().toString();
                City = city.getText().toString();
                District = district.getText().toString();
                State = state.getText().toString();
                Pin = pin.getText().toString();

                Intent intent = new Intent(Donor_signup.this, Donar_signup2.class);
                intent.putExtra("Name", Name);
                intent.putExtra("Dob", Dob);
                intent.putExtra("Address", Address);
                intent.putExtra("City", City);
                intent.putExtra("District", District);
                intent.putExtra("State", State);
                intent.putExtra("Pin", Pin);
                startActivity(intent);
            }
        });
    }
}
