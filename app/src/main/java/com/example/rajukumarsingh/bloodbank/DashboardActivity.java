package com.example.rajukumarsingh.bloodbank;

import android.*;
import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.rajukumarsingh.bloodbank.alarm.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int MY_PERMISSION_REQUEST_SEND_SMS =10 ;
    Button donor,acceptor,blood_bank,emergency;
    Double d1, d2;
    String str;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (!isConnected) {
            Toast.makeText(this, R.string.InternetError, Toast.LENGTH_SHORT).show();
            Intent i=new Intent(DashboardActivity.this,Offline.class);
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
                if ((ContextCompat.checkSelfPermission(DashboardActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(DashboardActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)){
                    ActivityCompat.requestPermissions(DashboardActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                } else{
                    startNextActivity();
                }

            }
        });
        acceptor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((ContextCompat.checkSelfPermission(DashboardActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(DashboardActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)){
                    ActivityCompat.requestPermissions(DashboardActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                } else{
                    startNextActivity2();
                }
            }
        });
        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((ContextCompat.checkSelfPermission(DashboardActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(DashboardActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)){
                    ActivityCompat.requestPermissions(DashboardActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                } else{
                    startNextActivity();
                }


            }

        });
//        blood_bank.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent3=new Intent(DashboardActivity.this,Blood_Bank.class);
//                startActivity(intent3);
//            }
//        });



        setAlarm(this);
        Intent i = new Intent(this, MyService.class);
        startService(i);
    }

    private void startNextActivity(){
        new com.example.rajukumarsingh.bloodbank.location.LocationCaptureTask(this){
            @Override
            public void afterLocationCapture(Location location) {
                if(location != null){
                    d1 = location.getLatitude();
                    d2 = location.getLongitude();

                    Intent i =new Intent(DashboardActivity.this,Bloodbank_list.class);
                    i.putExtra("Latitude",d1);
                    i.putExtra("Longitude",d2);

                    String s3 = Double.toString(d1);
                    String s4 = Double.toString(d2);

                    SharedPreferences mPrefs = getSharedPreferences("IDvalue", 0);
                    SharedPreferences.Editor editor = mPrefs.edit();
                    editor.putString("lo", s3);
                    editor.putString("la", s4);

                    editor.commit();
                    startActivity(i);
                } else{
                    Toast.makeText(DashboardActivity.this,R.string.LocationError,Toast.LENGTH_SHORT).show();
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


                    Intent i =new Intent(DashboardActivity.this,Donor.class);
                    i.putExtra("Latitude",d1);
                    i.putExtra("Longitude",d2);
                    startActivity(i);
                } else{
                    Toast.makeText(DashboardActivity.this,R.string.LocationError,Toast.LENGTH_SHORT).show();
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

                    Intent i =new Intent(DashboardActivity.this,Acceptor_2.class);
                    i.putExtra("Latitude",d1);
                    i.putExtra("Longitude",d2);
                    startActivity(i);
                } else{
                    Toast.makeText(DashboardActivity.this,R.string.LocationError,Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
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
                String message = getResources().getString(R.string.LocationRequest);

                AlertDialog alertDialog = new AlertDialog.Builder(DashboardActivity.this).create();
                alertDialog.setMessage(message);
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
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
                                .setTitle(getResources().getString(R.string.SMSPermission))
                                .setMessage(getResources().getString(R.string.NeedSMSPermission)).show();
                    }
                    else {
                        new android.app.AlertDialog.Builder(this)
                                .setTitle(getResources().getString(R.string.PermissionDeniedTitle))
                                .setMessage(getResources().getString(R.string.PermissionDeniedMessage)).show();
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


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.dashboard, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.about) {

        } else if (id == R.id.faq) {

        } else if (id == R.id.credits) {

        } else if (id == R.id.certs) {
            Intent intent = new Intent(DashboardActivity.this, DonationDateActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void setAlarm(Context context) {
        try {
            Intent intent = new Intent();
            Log.d(TAG, "Setting alarm");
            intent.setClass(context, AlarmReceiver.class);
            intent.putExtra("alarm_msg", "SHOW_NOTIFICATION");
            PendingIntent sender = PendingIntent.getBroadcast(context, 123456, intent,
                    PendingIntent.FLAG_CANCEL_CURRENT);

            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            long alarmInterval = AlarmManager.INTERVAL_HOUR;
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MINUTE, 0);
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            calendar.set(Calendar.SECOND, 0);
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy HH:mm:ss");
            Log.d(TAG, "next alarmtime=" + format.format(new Date(calendar.getTimeInMillis())));
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmInterval, sender);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static final String TAG = DashboardActivity.class.getSimpleName();

    @Override
    protected void onResume() {
        super.onResume();

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.SEND_SMS);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.SEND_SMS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Permission")
                        .setMessage("We need SMS permission to send out SMS to your emergency contacts in case of emergency")
                        .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(DashboardActivity.this,
                                        new String[]{android.Manifest.permission.SEND_SMS},
                                        100);
                            }
                        })
                        .show();

            } else {
                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        100);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }


}
