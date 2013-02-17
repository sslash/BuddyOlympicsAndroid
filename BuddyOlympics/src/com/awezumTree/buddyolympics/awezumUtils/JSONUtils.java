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
		JSONArray loc = new JSONArray();
		for (Object o : locations) {
			Location l = (Location) o;
			JSONObject point = new JSONObject();
			point.put(WaypointsSchema.LONGITUDE, l.getLongitude());
			point.put(WaypointsSchema.LATITUDE, l.getLatitude());
			point.put(WaypointsSchema.TIMESTAMP, l.getTime());
			loc.put(point);
		}
		return new JSONObject().put(ParticipantSchema.COORDINATES, loc);
	}

}
