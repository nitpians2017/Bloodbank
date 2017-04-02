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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class Bloodbank_list extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ListView listView;
    GetAllData get;
    BloodBankEntity[] bloodBankEntity,filteredBloodBankEntity;
    Double d1, d2, d3, d4, d6, d7;
    JSONObject jsonObject;
    JSONArray jsonArray;
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
        String src = bundle.getString("src");

        if(!"dashboard".equalsIgnoreCase(src)){
            ((Button) findViewById(R.id.contactDonor)).setVisibility(View.GONE);
        }

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

                a = Double.parseDouble(filteredBloodBankEntity[position].lati);
                b = Double.parseDouble(filteredBloodBankEntity[position].longi);
                i.putExtra("Latitude1", a);
                i.putExtra("Longitude1", b);
                i.putExtra("Latitude2", d1);
                i.putExtra("Longitude2", d2);
                i.putExtra("BBEobject",bloodBankEntity);
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
            bloodBankEntity = new BloodBankEntity[jsonArray.length()];
            d5 = jsonArray.length();

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    jsonObject = jsonArray.getJSONObject(i);
                    BloodBankEntity entity = new BloodBankEntity();
                    bloodBankEntity[i] = entity;
                    bloodBankEntity[i].h_name = jsonObject.getString("Hospital_Name");
                    bloodBankEntity[i].state = jsonObject.getString("State");
                    bloodBankEntity[i].lati = jsonObject.getString("Latitude");
                    bloodBankEntity[i].longi = jsonObject.getString("Longitude");
                    bloodBankEntity[i].add = jsonObject.getString("Address");
                    bloodBankEntity[i].contact = jsonObject.getString("Contact_Number");
                    bloodBankEntity[i].pincode = jsonObject.getString("Pin_Code");
                    bloodBankEntity[i].city = jsonObject.getString("City");
                    bloodBankEntity[i].AP = jsonObject.getString("A+");
                    bloodBankEntity[i].AN = jsonObject.getString("A-");
                    bloodBankEntity[i].ABP = jsonObject.getString("AB+");
                    bloodBankEntity[i].ABN = jsonObject.getString("AB-");
                    bloodBankEntity[i].BP = jsonObject.getString("B+");
                    bloodBankEntity[i].BN = jsonObject.getString("B-");
                    bloodBankEntity[i].OP = jsonObject.getString("O+");
                    bloodBankEntity[i].ON = jsonObject.getString("O-");
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

        filteredBloodBankEntity = new BloodBankEntity[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            if (flag) {
                d3 = Double.parseDouble(bloodBankEntity[i].lati);
                d4 = Double.parseDouble(bloodBankEntity[i].longi);
                Location.distanceBetween(d1, d2, d3, d4, result);

                result[0]/=1000;
                result[0] = Float.parseFloat(new DecimalFormat("####.##").format(result[0]));

                if (result[0] <= n) {
                    BloodBankEntity entity = new BloodBankEntity();
                    filteredBloodBankEntity[k] = entity;
                    filteredBloodBankEntity[k].dist = Float.toString(result[0]);
                    filteredBloodBankEntity[k].lati = bloodBankEntity[i].lati;
                    filteredBloodBankEntity[k].longi = bloodBankEntity[i].longi;
                    filteredBloodBankEntity[k].h_name = bloodBankEntity[i].h_name;
                    filteredBloodBankEntity[k].state = bloodBankEntity[i].state;
                    filteredBloodBankEntity[k].add = bloodBankEntity[i].add;
                    filteredBloodBankEntity[k].pincode = bloodBankEntity[i].pincode;
                    filteredBloodBankEntity[k].contact = bloodBankEntity[i].contact;
                    filteredBloodBankEntity[k].city = bloodBankEntity[i].city;
                    filteredBloodBankEntity[k].AP = bloodBankEntity[i].AP;
                    filteredBloodBankEntity[k].AN = bloodBankEntity[i].AN;
                    filteredBloodBankEntity[k].ABP = bloodBankEntity[i].ABP;
                    filteredBloodBankEntity[k].ABN = bloodBankEntity[i].ABN;
                    filteredBloodBankEntity[k].BP = bloodBankEntity[i].BP;
                    filteredBloodBankEntity[k].BN = bloodBankEntity[i].BN;
                    filteredBloodBankEntity[k].OP = bloodBankEntity[i].OP;
                    filteredBloodBankEntity[k].ON = bloodBankEntity[i].ON;
                    k++;
                }
            } else {

                d3 = Double.parseDouble(bloodBankEntity[i].lati);
                d4 = Double.parseDouble(bloodBankEntity[i].longi);
                Location.distanceBetween(d6, d7, d3, d4, result);

                result[0]/=1000;
                result[0] = Float.parseFloat(new DecimalFormat("####.##").format(result[0]));

                Location.distanceBetween(d6, d7, d1, d2, result1);

                result1[0]/=1000;
                result1[0] = Float.parseFloat(new DecimalFormat("####.##").format(result1[0]));

                if (result[0] <= n) {
                    BloodBankEntity entity = new BloodBankEntity();
                    filteredBloodBankEntity[k] = entity;
                    filteredBloodBankEntity[k].dist = Float.toString(Float.parseFloat(new DecimalFormat("####.##").format(result1[0] + result[0])));
                    filteredBloodBankEntity[k].lati = bloodBankEntity[i].lati;
                    filteredBloodBankEntity[k].longi = bloodBankEntity[i].longi;
                    filteredBloodBankEntity[k].h_name = bloodBankEntity[i].h_name;
                    filteredBloodBankEntity[k].state = bloodBankEntity[i].state;
                    filteredBloodBankEntity[k].add = bloodBankEntity[i].add;
                    filteredBloodBankEntity[k].pincode = bloodBankEntity[i].pincode;
                    filteredBloodBankEntity[k].contact = bloodBankEntity[i].contact;
                    filteredBloodBankEntity[k].city = bloodBankEntity[i].city;
                    filteredBloodBankEntity[k].AP = bloodBankEntity[i].AP;
                    filteredBloodBankEntity[k].AN = bloodBankEntity[i].AN;
                    filteredBloodBankEntity[k].ABP = bloodBankEntity[i].ABP;
                    filteredBloodBankEntity[k].ABN = bloodBankEntity[i].ABN;
                    filteredBloodBankEntity[k].BP = bloodBankEntity[i].BP;
                    filteredBloodBankEntity[k].BN = bloodBankEntity[i].BN;
                    filteredBloodBankEntity[k].OP = bloodBankEntity[i].OP;
                    filteredBloodBankEntity[k].ON = bloodBankEntity[i].ON;
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

            t1.setText(filteredBloodBankEntity[position].h_name);
            t2.setText(filteredBloodBankEntity[position].dist);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Bloodbank_list.this, Blood_bank_details.class);
                    intent.putExtra("Hospital", filteredBloodBankEntity[position].h_name);
                    intent.putExtra("Address", filteredBloodBankEntity[position].add);
                    intent.putExtra("City", filteredBloodBankEntity[position].city);
                    intent.putExtra("State", filteredBloodBankEntity[position].state);
                    intent.putExtra("Pincode", filteredBloodBankEntity[position].pincode);
                    intent.putExtra("Contact", filteredBloodBankEntity[position].contact);
                    intent.putExtra("AP", filteredBloodBankEntity[position].AP);
                    intent.putExtra("AN", filteredBloodBankEntity[position].AN);
                    intent.putExtra("ABP", filteredBloodBankEntity[position].ABP);
                    intent.putExtra("ABN", filteredBloodBankEntity[position].ABN);
                    intent.putExtra("BP", filteredBloodBankEntity[position].BP);
                    intent.putExtra("BN", filteredBloodBankEntity[position].BN);
                    intent.putExtra("OP", filteredBloodBankEntity[position].OP);
                    intent.putExtra("ON", filteredBloodBankEntity[position].ON);
                    startActivity(intent);
                }
            });
            return v;
        }
    }


    class BloodBankComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            BloodBankEntity s1 = (BloodBankEntity) o1;
            BloodBankEntity s2 = (BloodBankEntity) o2;

            return s1.dist.compareTo(s2.dist);
        }
    }

    }
