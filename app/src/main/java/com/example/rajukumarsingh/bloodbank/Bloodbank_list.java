package com.example.rajukumarsingh.bloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Bloodbank_list extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    ListView listView;
    String[] lati,longi,h_name,state,add,pincode,contact,city,AP,AN,ABP,ABN,BP,BN,OP,ON,lati1,longi1,h_name1,state1,add1,pincode1,contact1,city1,AP1,AN1,ABP1,ABN1,BP1,BN1,OP1,ON1;
    GetAllData get;
    Double d1,d2,d3,d4;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String[] dist;
    int d5,n=2;
    Spinner spinner;
    Button search;
    EditText city2;
    String str[]={"2","3","5","10","15","20"};
    int k=0;
    String url = "http://rahulraj47.coolpage.biz/gps_fetch.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloodbank_list);

        Bundle bundle=getIntent().getExtras();
        d1=bundle.getDouble("Latitude");
        d2=bundle.getDouble("Longitude");

        String s = Double.toString(d1);
        String s1 = Double.toString(d2);

        SharedPreferences mPrefs = getSharedPreferences("IDvalue", 0);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString("longi",s);
        editor.putString("latit",s1);

        editor.commit();



        search=(Button)findViewById(R.id.button11);
        city2= (EditText) findViewById(R.id.editText11);


        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent i = new Intent(Bloodbank_list.this,MapsActivity.class);
                Double a,b;
                a = Double.parseDouble(lati1[position]);
                b = Double.parseDouble(longi1[position]);
                i.putExtra("Latitude1",a);
                i.putExtra("Longitude1",b);
                i.putExtra("Latitude2",d1);
                i.putExtra("Longitude2",d2);
                i.putExtra("LatitudeArray",lati);
                i.putExtra("LongitudeArray",longi);
                i.putExtra("HospitalArray",h_name);
                i.putExtra("Length",d5);
                startActivity(i);
            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,str);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String City = city2.getText().toString();
                if (City.equals("Mumbai")==true || City.equals("mumbai")==true){
                    d1 = 19.025116;
                    d2 = 72.850411;
                    k=0;
                    getJSON();
                }
               else if (City.equals("Pune")==true || City.equals("pune")==true){
                    d1 = 18.515429;
                    d2 = 73.856326;
                    k=0;
                    getJSON();
                }
               else if (City.equals("Patna")==true || City.equals("patna")==true){
                    d1 = 25.616476;
                    d2 = 85.142010;
                    k=0;
                    getJSON();
                }
                else if (City.equals("kanpur")==true || City.equals("Kanpur")==true){
                    d1 = 26.462973;
                    d2 = 80.322834;
                    k=0;
                    getJSON();
                }
                else if (City.equals("Dhanbad")==true || City.equals("dhanbad")==true){
                    d1 = 23.814601;
                    d2 = 86.441113;
                    k=0;
                    getJSON();
                }
                else
                {
                    Toast.makeText(Bloodbank_list.this, "your city is not avilable in our database", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getJSON() {
        class GetUrls extends AsyncTask<String, Void, String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(Bloodbank_list.this, "Fetching Data", "Please Wait...", true, true);
            }

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                StringBuilder sb = new StringBuilder();
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return sb.toString().trim();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                get = new GetAllData(s);
                setData();
                progressDialog.dismiss();
            }
        }
        GetUrls g = new GetUrls();
        g.execute(url);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        k=0;
        n = Integer.parseInt((String) adapterView.getItemAtPosition(i));
        getJSON();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        getJSON();
    }


    public class GetAllData {
        public GetAllData(String s) {
            try {
                // json output in string
                jsonArray = new JSONArray(s);
                SharedPreferences mPrefs = getSharedPreferences("IDvalue", 0);
                SharedPreferences.Editor editor = mPrefs.edit();
                editor.putString("json",s);
                editor.commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void getAllData() {
            h_name = new String[jsonArray.length()];
            state = new String[jsonArray.length()];
            lati = new String[jsonArray.length()];
            longi = new String[jsonArray.length()];
            add = new String[jsonArray.length()];
            pincode = new String[jsonArray.length()];
            contact = new String[jsonArray.length()];
            AP = new String[jsonArray.length()];
            AN = new String[jsonArray.length()];
            ABP = new String[jsonArray.length()];
            ABN = new String[jsonArray.length()];
            BP = new String[jsonArray.length()];
            BN = new String[jsonArray.length()];
            OP = new String[jsonArray.length()];
            ON = new String[jsonArray.length()];
            city = new String[jsonArray.length()];
            d5=jsonArray.length();

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
                    AP[i] = jsonObject.getString("A+");
                    AN[i] = jsonObject.getString("A-");
                    ABP[i] = jsonObject.getString("AB+");
                    ABN[i] = jsonObject.getString("AB-");
                    BP[i] = jsonObject.getString("B+");
                    BN[i] = jsonObject.getString("B-");
                    OP[i] = jsonObject.getString("O+");
                    ON[i] = jsonObject.getString("O-");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setData() {
        class GetImage extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... params) {
                get.getAllData();
                return null;
            }

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Bloodbank_list.this, "Downloading...", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                loading.dismiss();
                calculate();
                Custom custom = new Custom();
                listView.setAdapter(custom);
            }
        }
        GetImage getImage = new GetImage();
        getImage.execute();
    }

    private void calculate() {
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
        AP1 = new String[jsonArray.length()];
        AN1 = new String[jsonArray.length()];
        ABP1 = new String[jsonArray.length()];
        ABN1 = new String[jsonArray.length()];
        BP1 = new String[jsonArray.length()];
        BN1 = new String[jsonArray.length()];
        OP1 = new String[jsonArray.length()];
        ON1 = new String[jsonArray.length()];

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
                AP1[k]=AP[i];
                AN1[k]=AN[i];
                ABP1[k]=ABP[i];
                ABN1[k]=ABN[i];
                BP1[k]=BP[i];
                BN1[k]=BN[i];
                OP1[k]=OP[i];
                ON1[k]=ON[i];
                k++;
            }
        }
    }

    public class Custom extends BaseAdapter {

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
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = getLayoutInflater().inflate(R.layout.custom_layout, parent, false);
            TextView t1, t2;
            ImageView imageView;

            t1 = (TextView) v.findViewById(R.id.textView);
            t2 = (TextView) v.findViewById(R.id.textView2);
            imageView = (ImageView) v.findViewById(R.id.imageButton);

            t1.setText(h_name1[position]);
            t2.setText(dist[position]);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Bloodbank_list.this,Blood_bank_details.class);
                    intent.putExtra("Hospital",h_name1[position]);
                    intent.putExtra("Address",add1[position]);
                    intent.putExtra("City",city1[position]);
                    intent.putExtra("State",state1[position]);
                    intent.putExtra("Pincode",pincode1[position]);
                    intent.putExtra("Contact",contact1[position]);
                    intent.putExtra("AP",AP1[position]);
                    intent.putExtra("AN",AN1[position]);
                    intent.putExtra("ABP",ABP1[position]);
                    intent.putExtra("ABN",ABN1[position]);
                    intent.putExtra("BP",BP1[position]);
                    intent.putExtra("BN",BN1[position]);
                    intent.putExtra("OP",OP1[position]);
                    intent.putExtra("ON",ON1[position]);
                    startActivity(intent);
                }
            });
            return v;
        }
    }


}
