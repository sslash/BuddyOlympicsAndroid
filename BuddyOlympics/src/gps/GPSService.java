package gps;

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
import android.widget.Toast;

public class GPSService extends Service implements LocationListener {
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5; // 5 meters
 
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 2; // 2 second
	
	private final Context mContext;
	
    Location location; // location
    double latitude; // latitude
    double longitude; // longitude
    
    protected LocationManager locationManager;

	private boolean isGPSEnabled = false;
	
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
 
        return latitude;
    }

    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
 
        return longitude;
    }
	
	public GPSService(Context context) {
		mContext = context;
		locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}
	
	public void subscribe() {
        if (!isGPSEnabled) {
        	showSettingsAlert();
        }
        else {
            if (location == null) {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                Log.d("GPS Enabled", "GPS Enabled");
                if (locationManager != null) {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            }
        }
	}
	
    public void unsuscribe(){
        if(locationManager != null){
            locationManager.removeUpdates(this);
        }
    }
	
    private void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
 
        alertDialog.setTitle("GPS is settings");

        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
 
        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);
 
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });
 
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
    }

	@Override
	public void onLocationChanged(Location loc) {
		
		location = loc;
		longitude = loc.getLongitude();
		latitude = loc.getLatitude();
		
		Log.d("KOKAIN", "new posish: "+ longitude + ", " + latitude);
	}

	@Override
	public void onProviderDisabled(String arg0) {
		Log.d("GPSEvent", "provider disabled - "+arg0);
		
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
