package com.awezumTree.buddyolympics.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 *  TODO: Remove all public accessors.
 *  Make them Private. Add gets/sets. Now. Really. Do it.
 * 
 * @author michaekg
 *
 */
public class Run implements Serializable {

	public Type type;

	public List<Runner> participants;

	public Date startTime;
	
	// TODO: This is only a temp solution.
	//Real solution should be moved to date object above
	public String startTimeString;
	
	public String id;

	public class Type {
		public String distance;
		public String time;
		public String getType() {
			if ( distance != null) return distance;
			else if (time != null) return time;
			else return null;
		}
	}

	
	/**
	 * Bane: "It does not matter how many lines of code there are in a function.
	 * What mattez is that it works!"
	 * 
	 * 
	 * @param data object with shieeeet (attributes for a run object)
	 */
	public void setAttributesFromJSON(JSONObject data) {
		JSONObject type;
		JSONArray participants;
		try {
			if (data.has("starttime"))
				startTimeString = data.getString("starttime");
			if (data.has("type")){
				type = data.getJSONObject("type");
				this.type = new Type();
				if ( type.has("distance"))
						this.type.distance = type.getString("distance");
				if ( type.has("time") )
					this.type.time = type.getString("time");				
			}
			
			if (data.has("_id"))
				this.id = data.getString("_id");
			
			if (data.has("participants")){
				this.participants = new ArrayList<Runner>();
				participants = data.getJSONArray("participants");
				for ( int i = 0; i < participants.length(); i++) {
					JSONObject runnerJSN = (JSONObject) participants.get(i);
					Runner r = new Runner();
					r.setId(runnerJSN.getString("_id"));
				//	r.setUsername(runnerJSN.getString("username"));
					this.participants.add(r);
				}
			}
			
		} catch (JSONException e) {
			Log.e("LOLCAT", "setAttributesFromJSON() - " + e.getMessage());
		}
	}

	@Override
	public String toString() {
		return "Run [type=" + type + ", participants=" + participants
				+ ", startTime=" + startTime + ", id=" + id + "]";
	}

	public String toJsonString() {
		StringBuilder builder = new StringBuilder().append("{\"type\":" + "{");

		// Only support for EITHER time or distance type.
		// Cause I'm Lazy. And in a hurry. Also I'm super kewl.
		if (type.time != null)
			builder.append("\"time\":" + "\"" + this.type.time + "\"");
		else if (type.distance != null)
			builder.append("\"time\":" + "\"" + this.type.distance + "\"");

		builder.append("}").append(",").append("\"starttime\":")
				.append("\"" + startTime.toString() + "\"").append(",")
				.append("\"participants\":[");
		for (Runner r : participants) {
			builder.append("{\"runner\":").append("\"" + r.getId() + "\"")
					.append("},");
		}
		;

		if (participants.size() > 0)
			builder.setLength(builder.length() - 1); // remove last ","

		builder.append("]}");

		Log.d("LOLCAT", "json: " + builder.toString());
		return builder.toString();
	}
}
