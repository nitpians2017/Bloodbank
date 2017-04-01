package com.example.rajukumarsingh.bloodbank;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Donar_signup2 extends AppCompatActivity {
    EditText blood_group, height, weight, last_blood_donation, last_platelete_donation;
    String Blood_group, Height, Weight, Last_blood_donation, Last_platelete_donation, date3, date4, will_of_donor;
    Button next1, date1, date2;
    String Name, Dob, Address, City, District, State, Pin;
    RadioButton radioButton, radioButton1;



    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };







//    DatePickerDialog.OnDateSetListener dd = new DatePickerDialog.OnDateSetListener() {
//        @Override
//        public void onDateSet(DatePicker view, int year, int month, int date) {
//            date3 = "" + date + "/" + month + "/" + year;
//            last_blood_donation.setText(date3);
//        }
//    };
//
//    DatePickerDialog.OnDateSetListener dd1 = new DatePickerDialog.OnDateSetListener() {
//        @Override
//        public void onDateSet(DatePicker view, int year, int month, int date) {
//            date4 = "" + date + "/" + month + "/" + year;
//            last_platelete_donation.setText(date4);
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar_signup2);
        blood_group = (EditText) findViewById(R.id.editText28);
        height = (EditText) findViewById(R.id.editText29);
        weight = (EditText) findViewById(R.id.editText30);
        last_blood_donation = (EditText) findViewById(R.id.editText31);
        last_platelete_donation = (EditText) findViewById(R.id.editText32);
        next1 = (Button) findViewById(R.id.button16);
        date1 = (Button) findViewById(R.id.button14);
        date2 = (Button) findViewById(R.id.button15);
        radioButton = (RadioButton) findViewById(R.id.radioButton);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton2);

        Bundle bundle = getIntent().getExtras();
        Name = bundle.getString("Name");
        Dob = bundle.getString("Dob");
        Address = bundle.getString("Address");
        City = bundle.getString("City");
        District = bundle.getString("District");
        State = bundle.getString("State");
        Pin = bundle.getString("Pin");


        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Donar_signup2.this, Donar_signup3.class);
                Blood_group = blood_group.getText().toString();
                Height = height.getText().toString();
                Weight = weight.getText().toString();
                Last_blood_donation = last_blood_donation.getText().toString();
                Last_platelete_donation = last_platelete_donation.getText().toString();
                intent.putExtra("Blood_group", Blood_group);
                intent.putExtra("Height", Height);
                intent.putExtra("Weight", Weight);
                intent.putExtra("Last_blood_donation", Last_blood_donation);
                intent.putExtra("Last_platelete_donation", Last_platelete_donation);
                intent.putExtra("Name", Name);
                intent.putExtra("Dob", Dob);
                intent.putExtra("Address", Address);
                intent.putExtra("City", City);
                intent.putExtra("District", District);
                intent.putExtra("State", State);
                intent.putExtra("Pin", Pin);
                intent.putExtra("Will_of_donor", will_of_donor);
                startActivity(intent);
            }
        });


      last_blood_donation.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              new DatePickerDialog(Donar_signup2.this, date, myCalendar
                      .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                      myCalendar.get(Calendar.DAY_OF_MONTH)).show();
          }
      });

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                will_of_donor = getResources().getString(R.string.YES);
            }
        });
        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                will_of_donor = getResources().getString(R.string.NO);
            }
        });
    }
    private void updateLabel() {

            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            last_blood_donation.setText(sdf.format(myCalendar.getTime()));
        }
    }

