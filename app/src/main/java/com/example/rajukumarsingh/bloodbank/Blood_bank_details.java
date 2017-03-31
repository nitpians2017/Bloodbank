package com.example.rajukumarsingh.bloodbank;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

public class Blood_bank_details extends AppCompatActivity {
    TextView textView,textView2,textView3,textView4,textView5,textView6,textView7,textView8,textView9,
            textView10,textView11,textView12,textView13,textView14,textView15;
    String h_name,state,addr,pincode,contact,city,AP,AN,ABP,ABN,BP,BN,OP,ON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank_details);
        textView = (TextView) findViewById(R.id.textView25);
        textView2 = (TextView) findViewById(R.id.textView27);
        textView3 = (TextView) findViewById(R.id.textView29);
        textView4 = (TextView) findViewById(R.id.textView32);
        textView5 = (TextView) findViewById(R.id.textView34);
        textView6 = (TextView) findViewById(R.id.textView36);
        textView7 = (TextView) findViewById(R.id.textView38);
        textView8 = (TextView) findViewById(R.id.textView40);
        textView9 = (TextView) findViewById(R.id.textView42);
        textView10 = (TextView) findViewById(R.id.textView44);
        textView11 = (TextView) findViewById(R.id.textView46);
        textView12 = (TextView) findViewById(R.id.textView48);
        textView13 = (TextView) findViewById(R.id.textView50);
        textView14 = (TextView) findViewById(R.id.textView52);
        textView15 = (TextView) findViewById(R.id.textView54);

        Bundle bundle=getIntent().getExtras();
        h_name = bundle.getString("Hospital");
        addr = bundle.getString("Address");
        city = bundle.getString("City");
        state = bundle.getString("State");
        pincode = bundle.getString("Pincode");
        contact = bundle.getString("Contact");
        AP = bundle.getString("AP");
        AN = bundle.getString("AN");
        ABP = bundle.getString("ABP");
        ABN = bundle.getString("ABN");
        BP = bundle.getString("BP");
        BN = bundle.getString("BN");
        OP = bundle.getString("OP");
        ON = bundle.getString("ON");
        String heading = "BLOOD BANK DETAILS";

        SpannableString content = new SpannableString(heading);
        content.setSpan(new UnderlineSpan(),0,heading.length(),0);
        textView.setText(content);

        textView2.setText(h_name);
        textView3.setText(addr);
        textView4.setText(city);
        textView5.setText(state);
        textView6.setText(pincode);

        SpannableString content1 = new SpannableString(contact);
        content1.setSpan(new UnderlineSpan(),0,contact.length(),0);
        textView7.setText(content1);
        textView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                        "tel", contact, null));
                startActivity(phoneIntent);
            }
        });

        textView8.setText(AP);
        textView9.setText(AN);
        textView10.setText(ABP);
        textView11.setText(ABN);
        textView12.setText(BP);
        textView13.setText(BN);
        textView14.setText(OP);
        textView15.setText(ON);
    }
}
