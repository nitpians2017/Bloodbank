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

public class Acceptor_SignUp extends AppCompatActivity {
    EditText name,dob,address,city,district,state,pin,blood_group,phone,emergency_contact,email,password;
    String Name,Dob,Address,City,District,State,Pin,Blood_group,Phone,Emergency_contact,Email,Password;
    RequestQueue requestQueue;
    Button submit;
    String url=Constants.ACCEPTOR_SIGNUP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptor__sign_up);
        name= (EditText) findViewById(R.id.editText38);
        dob= (EditText) findViewById(R.id.editText39);
        address= (EditText) findViewById(R.id.editText40);
        city= (EditText) findViewById(R.id.editText41);
        district= (EditText) findViewById(R.id.editText42);
        state= (EditText) findViewById(R.id.editText43);
        pin= (EditText) findViewById(R.id.editText44);
        blood_group= (EditText) findViewById(R.id.editText45);
        phone= (EditText) findViewById(R.id.editText46);
        emergency_contact= (EditText) findViewById(R.id.editText47);
        email= (EditText) findViewById(R.id.editText48);
        password= (EditText) findViewById(R.id.editText49);
        submit= (Button) findViewById(R.id.button18);
        requestQueue= Volley.newRequestQueue(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = name.getText().toString();
                Dob = dob.getText().toString();
                Address = address.getText().toString();
                City = city.getText().toString();
                District = district.getText().toString();
                State = state.getText().toString();
                Pin = pin.getText().toString();
                Blood_group = blood_group.getText().toString();
                Phone = phone.getText().toString();
                Emergency_contact = emergency_contact.getText().toString();
                Email = email.getText().toString();
                Password = password.getText().toString();

                SharedPreferences mPrefs = getSharedPreferences("IDvalue", 0);
                SharedPreferences.Editor editor = mPrefs.edit();
               // editor.putString("k", Emergency_contact);
                editor.putString("c",City);
                editor.commit();



                if (validateEmail(Email) == true && validatephone(Phone) == true && validatepin(Pin) == true &&
                        validatemergency1(Emergency_contact) == true && validatepassword(Password) == true) {

                    Toast.makeText(Acceptor_SignUp.this, getResources().getString(R.string.SignUpSuccess), Toast.LENGTH_SHORT).show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(Acceptor_SignUp.this, "" + volleyError, Toast.LENGTH_SHORT).show();
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
                            params.put("Phone", Phone);
                            params.put("Emergency_contact", Emergency_contact);
                            params.put("Email", Email);
                            params.put("Password", Password);

                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
                else {
                    Toast.makeText(Acceptor_SignUp.this, getResources().getString(R.string.SignUperror), Toast.LENGTH_SHORT).show();
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
        else
            Toast.makeText(this, R.string.EmailValidationError, Toast.LENGTH_SHORT).show();
        return retval;
    }
    boolean validatephone(String phone)
    {
        boolean retval=false;
        if (phone.length() == 10)
        {
            retval =true;
        }
        else
            Toast.makeText(this, R.string.PhoneValidationError, Toast.LENGTH_SHORT).show();
        return retval;
    }

    boolean validatemergency1(String emergencycontact1)
    {
        boolean retval=false;
        if (emergencycontact1.length() == 10)
        {
            retval =true;
        }
        else
            Toast.makeText(this, R.string.PhoneValidationError, Toast.LENGTH_SHORT).show();
        return  retval;
    }
    boolean validatepin(String pin)
    {
        boolean retval=false;
        if (pin.length() == 6)
        {
            retval =true;
        }
        else
            Toast.makeText(this, R.string.PincodeValidationError, Toast.LENGTH_SHORT).show();
        return  retval;
    }
    boolean validatepassword(String password1)
    {
        boolean retval=false;
        if (password1.length()<=25&&password1.length()>=8)
        {
            retval =true;
        }
        else
            Toast.makeText(this, R.string.PasswordValidationError, Toast.LENGTH_SHORT).show();
        return  retval;
    }
}
