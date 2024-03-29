package com.awezumTree.buddyolympics.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.awezumTree.buddyolympics.R;
import com.awezumTree.buddyolympics.awezumUtils.JSONUtils;
import com.awezumTree.buddyolympics.cache.UserCacheRegistry;
import com.awezumTree.buddyolympics.gps.GPSService;
import com.awezumTree.buddyolympics.restClient.AsyncTaskCallback;
import com.awezumTree.buddyolympics.restClient.RestPutClient;
import com.awezumTree.buddyolympics.schemas.RunSchema;

public class ActuallyRunningActivity extends Activity implements AsyncTaskCallback {
	private final int LOCATION_POLLING_TIMEOUT = 5000;
	private final int SERVER_PUSH_TIMEOUT = 10000;

	private GPSService gps;

	private List<Location> locations;
	private boolean running;
	private int pushedLocationIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actually_running);
		locations = new ArrayList<Location>();
		running = true;
		pushedLocationIndex = 0;
		gps = new GPSService(this);
	}

	@Override
	protected void onStart() {
		
		gps.subscribe();
		Log.d("ROADTEST", "subscribed to GPSService");
		
		Toast.makeText(getApplicationContext(), "LOL",
				Toast.LENGTH_LONG).show();

		super.onStart();

		runLocationPollingLoop();
		runServerPushLoop(this);
	}

	@Override
	protected void onDestroy() {
		gps.unsuscribe();
		stopLocationPolling();
		Log.d("ROADTEST", "unsubscribed from GPSService");
		super.onStop();
	}

	public void changeCoordinatesOnScreen(Double lat, Double longi) {
		((TextView) findViewById(R.id.latitude)).setText(Double.toString(lat));
		((TextView) findViewById(R.id.longitude)).setText(Double
				.toString(longi));
	}
	
	public void stopPolling(View view) {
		stopLocationPolling();
	}

	public void stopLocationPolling() {
		running = false;
	}

	private void runServerPushLoop(final ActuallyRunningActivity cb) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				int length = locations.size() - 1;
				Object[] subLocations = locations.subList(pushedLocationIndex, length).toArray();
				pushedLocationIndex = length;
				RestPutClient put = new RestPutClient(cb, getString(R.string.server_url)+"/runs/511fe7c45c923d508d000006/coords/511fa8d85805abfb89000003");	

				try {
					JSONObject json = JSONUtils.buildRunLocationUpdate(subLocations, UserCacheRegistry.get(cb));
					Log.d("ROADTEST", "Poll server with - " + json);
					put.setJsonBody(json);
					put.execute();
				} catch (JSONException e) {
					Log.e("PushLoopError", "Failed to create json - " + e.getMessage());
				}
				// PUT TO SERVER
				if (running) {
					runServerPushLoop(cb);
				}
			}
		}, SERVER_PUSH_TIMEOUT);
	}

	private void runLocationPollingLoop() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Location loc = gps.getLocation();
				Log.d("LocationPoll", "LOCATION - "+ loc);
				locations.add(loc);
				if (running) {
					runLocationPollingLoop();
				}
			}
		}, LOCATION_POLLING_TIMEOUT);
	}

	@Override
	public void callback(String res) {
		Log.d("SERVER RESPONSE", res);
	}
}
