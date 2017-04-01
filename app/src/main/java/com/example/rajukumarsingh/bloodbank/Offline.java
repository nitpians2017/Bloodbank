package com.example.rajukumarsingh.bloodbank;

import android.content.SharedPreferences;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Offline extends AppCompatActivity {
    String data,s1,s2;
    String[] lati,longi,h_name,state,add,pincode,contact,city,lati1,longi1,h_name1,state1,add1,pincode1,contact1,city1;
    String[] dist;
    JSONObject jsonObject;
    JSONArray jsonArray;
    Double d1,d2,d3,d4;
    ListView listView;
    int k=0,n=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);

        listView = (ListView) findViewById(R.id.listView3);

        SharedPreferences mPrefs = getSharedPreferences("IDvalue",0);
        data= mPrefs.getString("json", "");
        s1=mPrefs.getString("longi","");
        s2=mPrefs.getString("latit","");

        d1= Double.parseDouble(s1);
        d2= Double.parseDouble(s2);
        try {
            jsonArray = new JSONArray(data);
        h_name = new String[jsonArray.length()];
        state = new String[jsonArray.length()];
        lati = new String[jsonArray.length()];
        longi = new String[jsonArray.length()];
        add = new String[jsonArray.length()];
        pincode = new String[jsonArray.length()];
        contact = new String[jsonArray.length()];
        city = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                jsonObject = jsonArray.getJSONObject(i);
                h_name[i] = jsonObject.getString("Hospital_Name");
                state[i] = jsonObject.getString("State");
                lati[i] = jsonObject.getString("Latitude");
                longi[i] = jsonObject.getString("Longitude");
                add[i] = jsonObject.getString("Address");
                contact[i] = jsonObject.getString("Contact_Number");
                pincode[i] = jsonObject.getString("Pin_Code");
                city[i] = jsonObject.getString("City");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        float[] result = new float[2];
        dist = new String[jsonArray.length()];
        h_name1 = new String[jsonArray.length()];
        state1 = new String[jsonArray.length()];
        lati1 = new String[jsonArray.length()];
        longi1 = new String[jsonArray.length()];
        add1 = new String[jsonArray.length()];
        contact1 = new String[jsonArray.length()];
        pincode1 = new String[jsonArray.length()];
        city1 = new String[jsonArray.length()];

        for (int i=0;i<jsonArray.length();i++){
            d3 = Double.parseDouble(lati[i]);
            d4 = Double.parseDouble(longi[i]);
            Location.distanceBetween(d1,d2,d3,d4,result);
            if (result[0] <= (n*1000)) {
                dist[k] = Float.toString(result[0]);
                lati1[k]=lati[i];
                longi1[k]=longi[i];
                h_name1[k]=h_name[i];
                state1[k]=state[i];
                add1[k]=add[i];
                pincode1[k]=pincode[i];
                contact1[k]=contact[i];
                city1[k]=city[i];
                k++;
            }
        }
        MyCustom myCustom = new MyCustom();
        listView.setAdapter(myCustom);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public  class MyCustom extends BaseAdapter{

        @Override
        public int getCount() {
            return k;
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
        public View getView(int position, View view, ViewGroup parent) {
            View v = getLayoutInflater().inflate(R.layout.custom_layout3, parent, false);
            TextView t1, t2, t3, t4, t5, t6;
            t1 = (TextView) v.findViewById(R.id.textView92);
            t2 = (TextView) v.findViewById(R.id.textView93);
            t3 = (TextView) v.findViewById(R.id.textView94);
            t4 = (TextView) v.findViewById(R.id.textView95);
            t5 = (TextView) v.findViewById(R.id.textView96);
            t6 = (TextView) v.findViewById(R.id.textView97);

            t1.setText(h_name1[position]);
            t2.setText(add1[position]);
            t3.setText(city1[position]);
            t4.setText(state1[position]);
            t5.setText(contact1[position]);
            t6.setText(dist[position]);
            return v;
        }
    }
}

