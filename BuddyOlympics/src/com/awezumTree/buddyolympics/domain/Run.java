package com.awezumTree.buddyolympics.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import android.util.Log;

public class Run implements Serializable {
	
	public Type type;
	
	public List <Runner> participants;
	
	public Date startTime;	
	
	public class Type {
		public String distance;
		
		public String time;
	}
	
	public void setAttributesFromJSON(JSONObject data) {
		
	}
	
	public String toJsonString() {
		StringBuilder builder = new StringBuilder()
		.append("{\"type\":" + "{");
		
		// Only support for EITHER time or distance type.
		// Cause I'm Lazy. And in a hurry. Also I'm awesome.
		if ( type.time != null )
			builder.append("\"time\":" + "\""+this.type.time+"\"");
		else if ( type.distance != null) 
			builder.append("\"time\":" + "\""+this.type.distance+"\"");
			
		builder.append("}")
		.append(",")
		.append("\"starttime\":")
		.append("\"" + startTime.toString()+ "\"")
		.append(",")
		.append("\"participants\":[");
		for ( Runner r : participants) {
			builder
			.append("{\"runner\":")
			.append("\""+r.getId()+"\"")
			.append("},");
		};
		
		if ( participants.size() > 0)
			builder.setLength(builder.length()-1); //remove last ","
		
		builder.append("]}");
		
		Log.d("LOLCAT", "json: " + builder.toString());
		return builder.toString();		
	}
}
