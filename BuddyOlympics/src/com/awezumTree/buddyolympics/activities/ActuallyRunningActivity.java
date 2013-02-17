package com.awezumTree.buddyolympics.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.awezumTree.buddyolympics.R;
import com.awezumTree.buddyolympics.awezumUtils.JSONUtils;
import com.awezumTree.buddyolympics.cache.UserCacheRegistry;
import com.awezumTree.buddyolympics.gps.GPSService;
import com.awezumTree.buddyolympics.restClient.AsyncTaskCallback;
import com.awezumTree.buddyolympics.restClient.RestPutClient;

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
	}

	@Override
	protected void onStart() {
		gps = new GPSService(this);
		gps.subscribe();

		((TextView) findViewById(R.id.latitude)).setText(Double.toString(gps
				.getLocation().getLatitude()));
		((TextView) findViewById(R.id.latitude)).setText(Double.toString(gps
				.getLocation().getLongitude()));

		super.onStart();

		runLocationPollingLoop();
		runServerPushLoop(this);
	}

	@Override
	protected void onStop() {
		gps.unsuscribe();
		super.onStop();
	}

	public void changeCoordinatesOnScreen(Double lat, Double longi) {
		((TextView) findViewById(R.id.latitude)).setText(Double.toString(lat));
		((TextView) findViewById(R.id.longitude)).setText(Double
				.toString(longi));
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
				RestPutClient put = new RestPutClient(cb, getString(R.string.server_url)+"/runs");	
				try {
					put.setJsonBody(JSONUtils.buildRunLocationUpdate(subLocations, UserCacheRegistry.get(cb)));
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
				locations.add(loc);
				if (running) {
					runLocationPollingLoop();
				}
			}
		}, LOCATION_POLLING_TIMEOUT);
	}

	@Override
	public void callback(String res) {
		// TODO Auto-generated method stub
		
	}
}
