package com.example.rajukumarsingh.bloodbank;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSION_REQUEST_SEND_SMS =10 ;
    Button donor,acceptor,blood_bank,emergency;
    Double d1, d2;
    String str;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (!isConnected) {
            Toast.makeText(this, R.string.InternetError, Toast.LENGTH_SHORT).show();
            Intent i=new Intent(MainActivity.this,Offline.class);
            startActivity(i); //to do back button handling

        }

        donor= (Button) findViewById(R.id.donor);
        acceptor= (Button) findViewById(R.id.button6);
        blood_bank= (Button) findViewById(R.id.button7);
        emergency= (Button) findViewById(R.id.button8);
        mngaepermission();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);


        donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)){
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                } else{
                    startNextActivity1();
                }

            }
        });
        acceptor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)){
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                } else{
                    startNextActivity2();
                }
            }
        });
        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)){
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                } else{
                    startNextActivity();
                }


            }

        });
        blood_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3=new Intent(MainActivity.this,Blood_Bank.class);
                startActivity(intent3);
            }
        });
    }

    private void startNextActivity(){
        new com.example.rajukumarsingh.bloodbank.location.LocationCaptureTask(this){
            @Override
            public void afterLocationCapture(Location location) {
                if(location != null){
                    d1 = location.getLatitude();
                    d2 = location.getLongitude();

                    Intent i =new Intent(MainActivity.this,Bloodbank_list.class);
                    i.putExtra("Latitude",d1);
                    i.putExtra("Longitude",d2);
                    startActivity(i);
                } else{
                    Toast.makeText(MainActivity.this,R.string.LocationError,Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }



    private void startNextActivity1(){
        new com.example.rajukumarsingh.bloodbank.location.LocationCaptureTask(this){
            @Override
            public void afterLocationCapture(Location location) {
                if(location != null){
                    d1 = location.getLatitude();
                    d2 = location.getLongitude();


                    Intent i =new Intent(MainActivity.this,Donor.class);
                    i.putExtra("Latitude",d1);
                    i.putExtra("Longitude",d2);
                    startActivity(i);
                } else{
                    Toast.makeText(MainActivity.this,"Unable to fetch your location. Please try again",Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    } private void startNextActivity2(){
        new com.example.rajukumarsingh.bloodbank.location.LocationCaptureTask(this){
            @Override
            public void afterLocationCapture(Location location) {
                if(location != null){
                    d1 = location.getLatitude();
                    d2 = location.getLongitude();

                    Intent i =new Intent(MainActivity.this,Acceptor.class);
                    i.putExtra("Latitude",d1);
                    i.putExtra("Longitude",d2);
                    startActivity(i);
                } else{
                    Toast.makeText(MainActivity.this,"Unable to fetch your location. Please try again",Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==100)
        {
            if(grantResults.length == 2 && (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED))
            {
                startNextActivity();
            }
            else {
                String message = "For Blood Bank App to function properly, we need to access your current location.";

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setMessage(message);
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                    }
                });
                alertDialog.show();
            }
        }

        switch (requestCode)
        {
            case MY_PERMISSION_REQUEST_SEND_SMS :
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.SEND_SMS)){
                        new android.app.AlertDialog.Builder(this)
                                .setTitle("SEND SMS  PERMISSION")
                                .setMessage("you need to send sms permission for sending sms").show();
                    }
                    else {
                        new android.app.AlertDialog.Builder(this)
                                .setTitle("SEND SMS DENIED")
                                .setMessage("you denied for send sms permission go to setting for changing the permission").show();
                    }
                }


                break;
        }
    }




    private void mngaepermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[] {android.Manifest.permission.SEND_SMS},MY_PERMISSION_REQUEST_SEND_SMS);

        }
        else{
            sendsms();
        }
    }

    private void sendsms() {
       // Toast.makeText(this, "Send sms feature call", Toast.LENGTH_SHORT).show();
    }

}
