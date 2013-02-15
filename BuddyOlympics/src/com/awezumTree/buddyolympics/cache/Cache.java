package com.awezumTree.buddyolympics.cache;

import org.json.JSONObject;

import android.content.Context;

public class Cache {

	public JSONObject getCachedData() {
		return null;
	}
	
	public void setCacheData(JSONObject json, Context context) {
		try {
			FileHelper.writeToFile(json.toString(), context);
		} catch (CacheException e) {
			e.printStackTrace();
		}
	}
}
