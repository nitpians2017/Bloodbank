package com.example.rajukumarsingh.bloodbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class DonorSignupActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_donor_login);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }

    EditText name, dob, address, city, district, state, pin;
    String Name, Dob, Address, City, District, State, Pin;
//    Button date, next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.action_sign_up));
        setSupportActionBar(toolbar);



        name = (EditText) findViewById(R.id.editText3);
        dob = (EditText) findViewById(R.id.editText4);
        address = (EditText) findViewById(R.id.editText5);
        city = (EditText) findViewById(R.id.editText6);
        district = (EditText) findViewById(R.id.editText7);
        state = (EditText) findViewById(R.id.editText8);
        pin = (EditText) findViewById(R.id.editText9);
//        next = (Button) findViewById(R.id.button12);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Name = name.getText().toString();
                Dob = dob.getText().toString();
                Address = address.getText().toString();
                City = city.getText().toString();
                District = district.getText().toString();
                State = state.getText().toString();
                Pin = pin.getText().toString();

                Intent intent = new Intent(DonorSignupActivity.this, Donar_signup2.class);
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
