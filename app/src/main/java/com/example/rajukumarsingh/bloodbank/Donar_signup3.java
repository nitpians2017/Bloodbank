package com.example.rajukumarsingh.bloodbank;

import android.content.SharedPreferences;
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

public class Donar_signup3 extends AppCompatActivity {
    EditText phone,emergency_contact1,emergency_contact2,email,password;
    String Blood_group,Height,Weight,Last_blood_donation,Will_of_donor,Last_platelete_donation,Name,Dob,Address,City,District,State,Pin,Phone,Emergency_contact1,Emergency_contact2,Email,Password;
    RequestQueue requestQueue;
    Button submit;
    String url=Constants.DONOR_SIGNUP;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar_signup3);
        phone= (EditText) findViewById(R.id.editText33);
        emergency_contact1= (EditText) findViewById(R.id.editText34);
        emergency_contact2= (EditText) findViewById(R.id.editText35);
        email= (EditText) findViewById(R.id.editText36);
        password= (EditText) findViewById(R.id.editText37);
        submit= (Button) findViewById(R.id.button17);
        requestQueue= Volley.newRequestQueue(this);


        Bundle bundle=getIntent().getExtras();
        Name=bundle.getString("Name");
        Dob=bundle.getString("Dob");
        Will_of_donor=bundle.getString("Will_of_donor");
        Address=bundle.getString("Address");
        City=bundle.getString("City");
        District=bundle.getString("District");
        State=bundle.getString("State");
        Pin=bundle.getString("Pin");
        Blood_group=bundle.getString("Blood_group");
        Height=bundle.getString("Height");
        Weight=bundle.getString("Weight");
        Last_blood_donation=bundle.getString("Last_blood_donation");
        Last_platelete_donation=bundle.getString("Last_platelete_donation");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Phone=phone.getText().toString();
                Emergency_contact1=emergency_contact1.getText().toString();
                Emergency_contact2=emergency_contact2.getText().toString();
                Email=email.getText().toString();
                Password=password.getText().toString();






                if (validateEmail(Email)==true && validatephone(Phone)==true && validatepin(Pin)==true &&
                        validatemergency(Emergency_contact1)==true && validatemergency1(Emergency_contact2)==true && validatepassword(Password)==true) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Toast.makeText(Donar_signup3.this, "" + Name, Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(Donar_signup3.this, "" + volleyError, Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Name", Name);
                            params.put("Dob", Dob);
                            params.put("Address", Address);
                            params.put("City", City);
                            params.put("District", District);
                            params.put("State", State);
                            params.put("Pin", Pin);
                            params.put("Blood_group", Blood_group);
                            params.put("Height", Height);
                            params.put("Weight", Weight);
                            params.put("Last_blood_donation", Last_blood_donation);
                            params.put("Last_platelete_donation", Last_platelete_donation);
                            params.put("Phone", Phone);
                            params.put("Emergency_contact1", Emergency_contact1);
                            params.put("Emergency_contact2", Emergency_contact2);
                            params.put("Email", Email);
                            params.put("Password", Password);
                            params.put("Will_of_donor",Will_of_donor);
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
                else
                {
                    Toast.makeText(Donar_signup3.this, "you have entered something wrong", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    boolean validateEmail(String email){
        boolean retval=false;
        Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);
        if (EMAIL_PATTERN.matcher(email).matches()) {
            retval=true;
        }
        return retval;
    }
    boolean validatephone(String phone)
    {
        boolean retval=false;
        if (phone.length() == 10)
        {
            retval =true;
        }
        return retval;
    }

    boolean validatemergency(String emergencycontact)
    {
        boolean retval=false;
        if (emergencycontact.length() == 10)
        {
            retval =true;
        }
        return  retval;
    }
    boolean validatemergency1(String emergencycontact1)
    {
        boolean retval=false;
        if (emergencycontact1.length() == 10)
        {
            retval =true;
        }
        return  retval;
    }
    boolean validatepin(String pin)
    {
        boolean retval=false;
        if (pin.length() == 6)
        {
            retval =true;
        }
        return  retval;
    }
    boolean validatepassword(String password1)
    {
        boolean retval=false;
        if (password1.length()<=25&&password1.length()>=8)
        {
            retval =true;
        }
        return  retval;
    }
}
