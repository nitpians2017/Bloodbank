package com.example.rajukumarsingh.bloodbank.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public abstract class LocationClient implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

	private final String TAG = "LocationClient";

	private GoogleApiClient mGoogleApiClient;

	private LocationRequest mLocationRequest;

	private Context context;

	public LocationClient(Context context) {

		this.context = context;
		mGoogleApiClient = new GoogleApiClient.Builder(context.getApplicationContext()).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this)
				.build();
	}

	public void start() {
		mGoogleApiClient.connect();
	}

	public void stop() {
		mGoogleApiClient.disconnect();
	}

	@Override
	public void onConnected(Bundle bundle) {

		mLocationRequest = LocationRequest.create();
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setInterval(1000); // Update location every second
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			return;
		}
		LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
	}

	@Override
	public void onConnectionSuspended(int i) {
		Log.i(TAG, "GoogleApiClient connection has been suspend");
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		Log.i(TAG, "GoogleApiClient connection has failed");
	}

	
	
	
	
}