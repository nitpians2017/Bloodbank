package com.example.rajukumarsingh.bloodbank;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Donor_list extends AppCompatActivity {
    ListView listView;
    String data;
    JSONArray jsonArray;
    JSONObject jsonObject;
//    String[] name, blood_group, address, contact;
    ArrayList<DonorEntity> donorArray;

    ArrayList<DonorEntity> filteredArray;

    EditText searchText;
    MyCustom myCustom = new MyCustom();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_list);

        filteredArray = new ArrayList<DonorEntity>();

        searchText = (EditText)findViewById(R.id.searchText);

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String txt = s.toString();
                filteredArray.clear();

                for(int i = 0; i<donorArray.size(); i++){
                    DonorEntity entity = donorArray.get(i);
                    if(entity.blood_group.toLowerCase().contains(txt.toLowerCase())){
                        filteredArray.add(entity);
                    }
                }

                myCustom.notifyDataSetChanged();
            }
        });

        Bundle bundle = getIntent().getExtras();
        data = bundle.getString("JSON");
        try {
            jsonArray = new JSONArray(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        donorArray = new ArrayList<DonorEntity>();
//        name = new String[jsonArray.length()];
//        blood_group = new String[jsonArray.length()];
//        address = new String[jsonArray.length()];
//        contact = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                jsonObject = jsonArray.getJSONObject(i);
                DonorEntity entity = new DonorEntity();


                entity.name = jsonObject.getString("Name");
                entity.blood_group = jsonObject.getString("Blood_group");
                entity.address = jsonObject.getString("Address");
                entity.contact = jsonObject.getString("Phone");
                donorArray.add(entity);
                filteredArray.add(entity);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        listView = (ListView) findViewById(R.id.listview2);

        listView.setAdapter(myCustom);
    }

    public class MyCustom extends BaseAdapter {

        @Override
        public int getCount() {
            return filteredArray.size();
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
            t1.setText(filteredArray.get(position).name);
            t2.setText(filteredArray.get(position).blood_group);
            t3.setText(filteredArray.get(position).address);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(Donor_list.this).
                            setTitle(getResources().getString(R.string.SendSMS))
                            .setMessage(getResources().getString(R.string.contact_donor))
                            .setPositiveButton(R.string.YES, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(filteredArray.get(position).contact, null, getResources().getString(R.string.Message), null, null);
                                    Toast.makeText(Donor_list.this,R.string.sms_sent_donor,Toast.LENGTH_LONG).show();
                                }
                            }).setNegativeButton(R.string.NO, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();

                }
            });
            return v;
        }
    }
}
