package com.example.rajukumarsingh.bloodbank;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class Bloodbank_list extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ListView listView;
    String[] lati, longi, h_name, state, add, pincode, contact, city, AP, AN, ABP, ABN, BP, BN, OP, ON, lati1, longi1, h_name1, state1, add1, pincode1, contact1, city1, AP1, AN1, ABP1, ABN1, BP1, BN1, OP1, ON1;
    GetAllData get;
    Double d1, d2, d3, d4, d6, d7;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String[] dist;
    int d5, n = 2;
    Spinner spinner;
    Button search;
    EditText city2;
    String str[] = {"2", "3", "5", "10", "15", "20"};
    int k = 0;
    boolean flag = true;
    String url = Constants.GPS_FETCH;
    StringBuilder sb = new StringBuilder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloodbank_list);

        Bundle bundle = getIntent().getExtras();
        d1 = bundle.getDouble("Latitude");
        d2 = bundle.getDouble("Longitude");

        Geocoder geocoder = new Geocoder(Bloodbank_list.this, Locale.ENGLISH);
        try {
            List<Address> myadd = geocoder.getFromLocation(d1, d2, 10);
            Address address = myadd.get(0);

            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                sb.append(address.getAddressLine(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        String s = Double.toString(d1);
        String s1 = Double.toString(d2);

        SharedPreferences mPrefs = getSharedPreferences("IDvalue", 0);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString("longi", s);
        editor.putString("latit", s1);

        editor.commit();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); //HIDE DEFAULT KEYBOARD

        search = (Button) findViewById(R.id.button11);
        city2 = (EditText) findViewById(R.id.editText11);


        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent i = new Intent(Bloodbank_list.this, MapsActivity.class);
                Double a, b;

                a = Double.parseDouble(lati1[position]);
                b = Double.parseDouble(longi1[position]);
                i.putExtra("Latitude1", a);
                i.putExtra("Longitude1", b);
                i.putExtra("Latitude2", d1);
                i.putExtra("Longitude2", d2);
                i.putExtra("LatitudeArray", lati);
                i.putExtra("LongitudeArray", longi);
                i.putExtra("HospitalArray", h_name);
                i.putExtra("Length", d5);
                startActivity(i);
            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, str);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String City = city2.getText().toString();
                if ("Mumbai".equalsIgnoreCase(City)) {
                    d6 = 19.025116;
                    d7 = 72.850411;
                    flag = false;
                    k = 0;
                    getJSON();
                } else if ("Pune".equalsIgnoreCase(City)) {
                    d6 = 18.515429;
                    d7 = 73.856326;
                    flag = false;
                    k = 0;
                    getJSON();
                } else if ("Patna".equalsIgnoreCase(City)) {
                    d6 = 25.616476;
                    d7 = 85.142010;
                    k = 0;
                    getJSON();
                } else if ("Kanpur".equalsIgnoreCase(City)) {
                    d6 = 26.462973;
                    d7 = 80.322834;
                    flag = false;
                    k = 0;
                    getJSON();
                } else if ("Dhanbad".equalsIgnoreCase(City)) {
                    d6 = 23.814601;
                    d7 = 86.441113;
                    flag = false;
                    k = 0;
                    getJSON();
                } else {
                    Toast.makeText(Bloodbank_list.this, R.string.CityError, Toast.LENGTH_SHORT).show();
                }
            }
        });

        ((Button) findViewById(R.id.contactDonor)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mPrefs = getSharedPreferences("IDvalue", 0);
                final String str = mPrefs.getString("k", "");

                new AlertDialog.Builder(Bloodbank_list.this).setTitle(getResources().getString(R.string.SendSMS))
                        .setMessage(getResources().getString(R.string.SendEmergency))
                        .setPositiveButton(getResources().getString(R.string.YES), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (str == null || str.length() < 10) {
                                    AlertDialog alertDialog = new AlertDialog.Builder(Bloodbank_list.this).create();
                                    final EditText input = new EditText(Bloodbank_list.this);
                                    input.setHint(getResources().getString(R.string.MobileNumber));
                                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.MATCH_PARENT);
                                    input.setLayoutParams(lp);
                                    alertDialog.setView(input);
                                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            String number = input.getText().toString();
                                            SmsManager smsManager = SmsManager.getDefault();
                                            smsManager.sendTextMessage(number, null, "HELP!! I'm at address : " + sb, null, null);
                                        }
                                    });
                                    alertDialog.show();


                                } else {
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(str, null, "HELP!!" + sb, null, null);
                                }
                            }
                        })
                        .setTitle(getResources().getString(R.string.SendSMS))
                        .setMessage(getResources().getString(R.string.SendEmergency))
                        .setNegativeButton(getResources().getString(R.string.NO), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
            }
        });

    }

    private void getJSON() {
        class GetUrls extends AsyncTask<String, Void, String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(Bloodbank_list.this, getResources().getString(R.string.Fetching), getResources().getString(R.string.Wait), true, true);
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
        k = 0;
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
                editor.putString("json", s);
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
            d5 = jsonArray.length();

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
                try {
                    get.getAllData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Bloodbank_list.this, getResources().getString(R.string.downloading), getResources().getString(R.string.Wait), false, false);
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                try {
                    loading.dismiss();
                    calculate();
                    Custom custom = new Custom();
                    listView.setAdapter(custom);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        GetImage getImage = new GetImage();
        getImage.execute();
    }

    private void calculate() {
        float[] result = new float[2];
        float[] result1 = new float[2];
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

        for (int i = 0; i < jsonArray.length(); i++) {
            if (flag) {
                d3 = Double.parseDouble(lati[i]);
                d4 = Double.parseDouble(longi[i]);
                Location.distanceBetween(d1, d2, d3, d4, result);
                result[0]/=1000;
                new DecimalFormat("####.##").format(result[0]);
                if (result[0] <= n) {
                    dist[k] = Float.toString(result[0]);
                    lati1[k] = lati[i];
                    longi1[k] = longi[i];
                    h_name1[k] = h_name[i];
                    state1[k] = state[i];
                    add1[k] = add[i];
                    pincode1[k] = pincode[i];
                    contact1[k] = contact[i];
                    city1[k] = city[i];
                    AP1[k] = AP[i];
                    AN1[k] = AN[i];
                    ABP1[k] = ABP[i];
                    ABN1[k] = ABN[i];
                    BP1[k] = BP[i];
                    BN1[k] = BN[i];
                    OP1[k] = OP[i];
                    ON1[k] = ON[i];
                    k++;
                }
            } else {

                d3 = Double.parseDouble(lati[i]);
                d4 = Double.parseDouble(longi[i]);
                Location.distanceBetween(d6, d7, d3, d4, result);
                result[0]/=1000;
                new DecimalFormat("####.##").format(result[0]);
                Location.distanceBetween(d6, d7, d1, d2, result1);
                result1[0]/=1000;
                new DecimalFormat("####.##").format(result1[0]);
                if (result[0] <= n) {
                    dist[k] = Float.toString(result[0] + result1[0]);
                    lati1[k] = lati[i];
                    longi1[k] = longi[i];
                    h_name1[k] = h_name[i];
                    state1[k] = state[i];
                    add1[k] = add[i];
                    pincode1[k] = pincode[i];
                    contact1[k] = contact[i];
                    city1[k] = city[i];
                    AP1[k] = AP[i];
                    AN1[k] = AN[i];
                    ABP1[k] = ABP[i];
                    ABN1[k] = ABN[i];
                    BP1[k] = BP[i];
                    BN1[k] = BN[i];
                    OP1[k] = OP[i];
                    ON1[k] = ON[i];
                    k++;
                }
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
                    Intent intent = new Intent(Bloodbank_list.this, Blood_bank_details.class);
                    intent.putExtra("Hospital", h_name1[position]);
                    intent.putExtra("Address", add1[position]);
                    intent.putExtra("City", city1[position]);
                    intent.putExtra("State", state1[position]);
                    intent.putExtra("Pincode", pincode1[position]);
                    intent.putExtra("Contact", contact1[position]);
                    intent.putExtra("AP", AP1[position]);
                    intent.putExtra("AN", AN1[position]);
                    intent.putExtra("ABP", ABP1[position]);
                    intent.putExtra("ABN", ABN1[position]);
                    intent.putExtra("BP", BP1[position]);
                    intent.putExtra("BN", BN1[position]);
                    intent.putExtra("OP", OP1[position]);
                    intent.putExtra("ON", ON1[position]);
                    startActivity(intent);
                }
            });
            return v;
        }
    }


}
