package com.example.rajukumarsingh.bloodbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Acceptor_2 extends AppCompatActivity {
    Button button,button2;
    Double d1,d2;
    String JSON;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptor_2);
        button = (Button) findViewById(R.id.button3);
        button2 = (Button) findViewById(R.id.button5);



        Bundle bundle = getIntent().getExtras();
        d1 = bundle.getDouble("Latitude");
        d2 = bundle.getDouble("Longitude");
        JSON = bundle.getString("JSON");

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
    }
}
