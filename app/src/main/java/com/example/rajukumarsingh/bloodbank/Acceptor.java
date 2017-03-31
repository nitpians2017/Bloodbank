package com.example.rajukumarsingh.bloodbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Acceptor extends AppCompatActivity {
    Button log_in,Sign_up;
    Double d1,d2;
//testing
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Sign_up= (Button) findViewById(R.id.button);
        log_in= (Button) findViewById(R.id.button2);

        Bundle bundle = getIntent().getExtras();
        d1 = bundle.getDouble("Latitude");
        d2 = bundle.getDouble("Longitude");

        Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Acceptor.this,Acceptor_SignUp.class);
                startActivity(intent);
            }
        });
        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Acceptor.this,Acceptor_Login.class);
                intent.putExtra("Latitude",d1);
                intent.putExtra("Longitude",d2);
                startActivity(intent);
            }
        });
    }

}
