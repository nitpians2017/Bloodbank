package com.example.rajukumarsingh.bloodbank;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class DonorSignupActivity extends AppCompatActivity {


    EditText name, dob, address, city, district, state, pin;
    EditText phone, emergency_contact1, emergency_contact2, email, password;
    EditText blood_group, height, weight, last_blood_donation, last_platelete_donation;
    String Blood_group, Height, Weight, Last_blood_donation, Last_platelete_donation, will_of_donor;
    String Phone, Emergency_contact1, Emergency_contact2, Email, Password;
    String Name, Dob, Address, City, District, State, Pin;
    RadioButton radioButton, radioButton1;
    RequestQueue requestQueue;
    String url = Constants.DONOR_SIGNUP;

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

    Calendar myCalendar1 = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar1.set(Calendar.YEAR, year);
            myCalendar1.set(Calendar.MONTH, monthOfYear);
            myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel1();
        }

    };


    private static final String TAG = DonorSignupActivity.class.getSimpleName();

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
        blood_group = (EditText) findViewById(R.id.editText28);
        height = (EditText) findViewById(R.id.editText29);
        weight = (EditText) findViewById(R.id.editText30);
        last_blood_donation = (EditText) findViewById(R.id.editText31);
        last_platelete_donation = (EditText) findViewById(R.id.editText32);
        radioButton = (RadioButton) findViewById(R.id.radioButton);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton2);
        phone = (EditText) findViewById(R.id.editText33);
        emergency_contact1 = (EditText) findViewById(R.id.editText34);
        emergency_contact2 = (EditText) findViewById(R.id.editText35);
        email = (EditText) findViewById(R.id.editText36);
        password = (EditText) findViewById(R.id.editText37);
        requestQueue = Volley.newRequestQueue(this);


        last_blood_donation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(DonorSignupActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        last_platelete_donation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(DonorSignupActivity.this, date1, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
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
                Blood_group = blood_group.getText().toString();
                Height = height.getText().toString();
                Weight = weight.getText().toString();
                Last_blood_donation = last_blood_donation.getText().toString();
                Last_platelete_donation = last_platelete_donation.getText().toString();
                Phone = phone.getText().toString();
                Emergency_contact1 = emergency_contact1.getText().toString();
                Emergency_contact2 = emergency_contact2.getText().toString();
                Email = email.getText().toString();
                Password = password.getText().toString();

                SharedPreferences mPrefs = getSharedPreferences("IDvalue", 0);
                SharedPreferences.Editor editor = mPrefs.edit();
                editor.putString("k", Emergency_contact1);
                editor.putString("name",Name);
                editor.commit();




                if (validateEmail(Email) == true && validatephone(Phone) == true && validatepin(Pin) == true &&
                        validatemergency(Emergency_contact1) == true && validatemergency1(Emergency_contact2) == true && validatepassword(Password) == true) {

                    Toast.makeText(DonorSignupActivity.this, R.string.SignUpSuccess, Toast.LENGTH_SHORT).show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Toast.makeText(DonorSignupActivity.this, "" + Name, Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(DonorSignupActivity.this, "" + volleyError, Toast.LENGTH_SHORT).show();
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
                            params.put("Will_of_donor", will_of_donor);
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                } else {
                    Toast.makeText(DonorSignupActivity.this, R.string.SignUperror, Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        last_blood_donation.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel1() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        last_platelete_donation.setText(sdf.format(myCalendar1.getTime()));
    }

    boolean validateEmail(String email) {
        boolean retval = false;
        Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);
        if (EMAIL_PATTERN.matcher(email).matches()) {
            retval = true;
        } else
            Toast.makeText(this, R.string.EmailValidationError, Toast.LENGTH_SHORT).show();
        return retval;
    }

    boolean validatephone(String phone) {
        boolean retval = false;
        if (phone.length() == 10) {
            retval = true;
        } else
            Toast.makeText(this, R.string.PhoneValidationError, Toast.LENGTH_SHORT).show();
        return retval;
    }

    boolean validatemergency(String emergencycontact) {
        boolean retval = false;
        if (emergencycontact.length() == 10) {
            retval = true;
        } else
            Toast.makeText(this, R.string.PhoneValidationError, Toast.LENGTH_SHORT).show();
        return retval;
    }

    boolean validatemergency1(String emergencycontact1) {
        boolean retval = false;
        if (emergencycontact1.length() == 10) {
            retval = true;
        } else
            Toast.makeText(this, R.string.PhoneValidationError, Toast.LENGTH_SHORT).show();
        return retval;
    }

    boolean validatepin(String pin) {
        boolean retval = false;
        if (pin.length() == 6) {
            retval = true;
        } else
            Toast.makeText(this, R.string.PincodeValidationError, Toast.LENGTH_SHORT).show();
        return retval;
    }

    boolean validatepassword(String password1) {
        boolean retval = false;
        if (password1.length() <= 25 && password1.length() >= 8) {
            retval = true;
        } else
            Toast.makeText(this, R.string.PasswordValidationError, Toast.LENGTH_SHORT).show();
        return retval;
    }
}
