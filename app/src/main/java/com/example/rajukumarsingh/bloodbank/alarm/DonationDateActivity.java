package com.example.rajukumarsingh.bloodbank.alarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;

import com.example.rajukumarsingh.bloodbank.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DonationDateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_date);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePicker picker = ((DatePicker)findViewById(R.id.datePicker));
                SimpleDateFormat dateformat = new SimpleDateFormat("d MMM''yy");

                String dt = dateformat.format(new Date(picker.getYear(), picker.getMonth(), picker.getDayOfMonth()));

                Intent intent = new Intent();
                intent.setClass(DonationDateActivity.this,AddCertificateActivity.class);
                intent.putExtra("date",dt);
                startActivity(intent);
            }
        });
    }

}
