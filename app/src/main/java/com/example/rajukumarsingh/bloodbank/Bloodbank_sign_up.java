package com.example.rajukumarsingh.bloodbank;

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

import static android.R.attr.name;

public class Bloodbank_sign_up extends AppCompatActivity {
    Button signup;
    EditText blood_bank_name,reg_no,address,city,district,state,pin_code ,contact,email;
    String Blood_Bank_Name,Reg_No,Address,City,District,State,Pin_Code,Contact,Email;
    RequestQueue requestQueue;
    String url="http://rahulraj47.coolpage.biz/blood_bank.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloodbank_sign_up);

        blood_bank_name= (EditText) findViewById(R.id.editText10);
        reg_no= (EditText) findViewById(R.id.editText12);
        address= (EditText) findViewById(R.id.editText13);
        city= (EditText) findViewById(R.id.editText14);
        district= (EditText) findViewById(R.id.editText15);
        state= (EditText) findViewById(R.id.editText16);
        pin_code= (EditText) findViewById(R.id.editText17);
        contact= (EditText) findViewById(R.id.editText18);
        email= (EditText) findViewById(R.id.editText19);
        signup= (Button) findViewById(R.id.signup);
        requestQueue= Volley.newRequestQueue(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Blood_Bank_Name=blood_bank_name.getText().toString();
                Reg_No=reg_no.getText().toString();
                Address=address.getText().toString();
                City=city.getText().toString();
                District=district.getText().toString();
                State=state.getText().toString();
                Pin_Code=pin_code.getText().toString();
                Contact=contact.getText().toString();
                Email=email.getText().toString();


                if (validateEmail(Email)==true && validatephone(Contact)==true) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Toast.makeText(Bloodbank_sign_up.this, "" +Blood_Bank_Name, Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(Bloodbank_sign_up.this, "" + volleyError, Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Blood_Bank_Name", Blood_Bank_Name);
                            params.put("Reg_No", Reg_No);
                            params.put("Address", Address);
                            params.put("City", City);
                            params.put("District", District);
                            params.put("State", State);
                            params.put("Pin_Code", Pin_Code);
                            params.put("Contact", Contact);
                            params.put("Email", Email);

                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
                else
                {

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
    boolean validatepin(String pin)
    {
        boolean retval=false;
        if (pin.length() == 6)
        {
            retval =true;
        }
        return  retval;
    }
}
