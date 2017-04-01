package com.example.rajukumarsingh.bloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Donor_list extends AppCompatActivity {
    ListView listView;
    String data;
    JSONArray jsonArray;
    JSONObject jsonObject;
    String[] name, blood_group, address, contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_list);

        Bundle bundle = getIntent().getExtras();
        data = bundle.getString("JSON");
        try {
            jsonArray = new JSONArray(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        name = new String[jsonArray.length()];
        blood_group = new String[jsonArray.length()];
        address = new String[jsonArray.length()];
        contact = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                jsonObject = jsonArray.getJSONObject(i);
                name[i] = jsonObject.getString("Name");
                blood_group[i] = jsonObject.getString("Blood_group");
                address[i] = jsonObject.getString("Address");
                contact[i] = jsonObject.getString("Phone");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        listView = (ListView) findViewById(R.id.listview2);
        MyCustom myCustom = new MyCustom();
        listView.setAdapter(myCustom);
    }

    public class MyCustom extends BaseAdapter {

        @Override
        public int getCount() {
            return jsonArray.length();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View converVview, ViewGroup parent) {
            View v = getLayoutInflater().inflate(R.layout.custom_layout2, parent, false);
            TextView t1, t2, t3;
            ImageView imageView;

            t1 = (TextView) v.findViewById(R.id.textView56);
            t2 = (TextView) v.findViewById(R.id.textView88);
            t3 = (TextView) v.findViewById(R.id.textView90);
            imageView = (ImageView) v.findViewById(R.id.imageView2);
            t1.setText(name[position]);
            t2.setText(blood_group[position]);
            t3.setText(address[position]);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(contact[position], null, getResources().getString(R.string.Message), null, null);
                }
            });
            return v;
        }
    }
}
