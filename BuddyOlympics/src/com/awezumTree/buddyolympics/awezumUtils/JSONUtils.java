package com.awezumTree.buddyolympics.awezumUtils;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;

import com.awezumTree.buddyolympics.domain.Runner;
import com.awezumTree.buddyolympics.domain.User;

public class JSONUtils {
	
	public static JSONObject buildRunLocationUpdate(Object[] locations, User user) throws JSONException {
		JSONObject payload = new JSONObject();
		JSONObject participant = new JSONObject();
		participant.put(Runner.RUNNER, user.getRunner());
		JSONArray loc = new JSONArray();
		for (Object o : locations) {
			Location l = (Location) o;
			JSONObject point = new JSONObject();
			point.put("longitude", l.getLongitude());
			point.put("latitude", l.getLatitude());
			point.put("timestamp", new Date(l.getTime()));
			loc.put(point);
		}
		participant.put("coordinates", loc);
		payload.put("participant", participant);
		return payload;
	}

}
