package com.awezumTree.buddyolympics.gps;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public class GPSService extends Service implements LocationListener {

	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5;

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 2;

	private final Context mContext;

	private Location location;

	protected LocationManager locationManager;

	public GPSService(Context context) {
		mContext = context;
		locationManager = (LocationManager) mContext
				.getSystemService(LOCATION_SERVICE);
	}

	public void subscribe() {
		if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			showSettingsAlert();
			subscribe();
		} else {
			if (location == null) {
				locationManager.requestLocationUpdates(
						LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
						MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
				Log.d("GPS Enabled", "GPS Enabled");
				if (locationManager != null) {
					location = locationManager
							.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				}
			}
		}
	}

	public void unsuscribe() {
		if (locationManager != null) {
			locationManager.removeUpdates(this);
		}
	}

	private void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

		alertDialog.setTitle("GPS is settings");

		alertDialog
				.setMessage("GPS is not enabled. Do you want to go to settings menu?");

		// On pressing Settings button
		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						mContext.startActivity(intent);
					}
				});

		// on pressing cancel button
		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}

	public Location getLocation() {
		if (location != null) { 
			Log.d("GPSEvent", "Queried for location. Offer - " + location.toString());
		} else {
			Log.e("GPSEvent", "Location == null");
		}
		return location;
	}

	@Override
	public void onLocationChanged(Location loc) {
		location = loc;
		Log.d("GPSEvent", "new posish: " + location.toString());
	}

	@Override
	public void onProviderDisabled(String arg0) {
		Log.d("GPSEvent", "provider disabled - " + arg0);
	}

	@Override
	public void onProviderEnabled(String arg0) {
		Log.d("GPSEvent", "provider enabled - " + arg0);
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		Log.d("GPSEvent", "status changed - " + arg0);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		Log.d("GPSEvent", "bind - " + arg0.toString());
		return null;
	}
}
