package com.awezumTree.buddyolympics.awezumUtils;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;

import com.awezumTree.buddyolympics.domain.User;
import com.awezumTree.buddyolympics.schemas.ParticipantSchema;
import com.awezumTree.buddyolympics.schemas.WaypointsSchema;

public class JSONUtils {
	
	public static JSONObject buildRunLocationUpdate(Object[] locations, User user) throws JSONException {
		JSONObject payload = new JSONObject();
		JSONObject participant = new JSONObject();
		participant.put(ParticipantSchema.RUNNER, user.getRunner());
		JSONArray loc = new JSONArray();
		for (Object o : locations) {
			Location l = (Location) o;
			JSONObject point = new JSONObject();
			point.put(WaypointsSchema.LONGITUDE, l.getLongitude());
			point.put(WaypointsSchema.LATITUDE, l.getLatitude());
			point.put(WaypointsSchema.TIMESTAMP, new Date(l.getTime()));
			loc.put(point);
		}
		participant.put("coordinates", loc);
		payload.put("participant", participant);
		return payload;
	}

}
