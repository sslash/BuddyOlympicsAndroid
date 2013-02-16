package com.awezumTree.buddyolympics.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.awezumTree.buddyolympics.R;
import com.awezumTree.buddyolympics.gps.GPSService;

public class ActuallyRunningActivity extends Activity {
	GPSService gps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actually_running);

	}
	
	
	
	@Override
	protected void onStart() {
		gps = new GPSService(this);
		gps.subscribe();
		
		((TextView)findViewById (R.id.latitude)).setText(Double.toString(gps.getLatitude()));
		((TextView)findViewById (R.id.longitude)).setText(Double.toString(gps.getLongitude()));
		
		super.onStart();
	}



	@Override
	protected void onStop() {
		gps.unsuscribe();
		super.onStop();
	}



	public void changeCoordinatesOnScreen(Double lat, Double longi) {
		((TextView)findViewById (R.id.latitude)).setText(Double.toString(lat));
		((TextView)findViewById (R.id.longitude)).setText(Double.toString(longi));
	}

	
}
