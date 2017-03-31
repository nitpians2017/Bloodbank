package com.example.rajukumarsingh.bloodbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Blood_Bank extends AppCompatActivity {
    Button sign_up,login,details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood__bank);
        sign_up= (Button) findViewById(R.id.button);
        login= (Button) findViewById(R.id.button2);
        details= (Button) findViewById(R.id.button4);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Blood_Bank.this,Bloodbank_sign_up.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Blood_Bank.this,Bloodbank_login.class);
                startActivity(intent);
            }
        });


        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Intent intent=new Intent(Blood_Bank.this,BB_Details.class);
                //startActivity(intent);
            }
        });
    }
}
