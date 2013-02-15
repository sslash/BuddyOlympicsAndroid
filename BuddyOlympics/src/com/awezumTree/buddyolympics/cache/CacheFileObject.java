package com.awezumTree.buddyolympics.cache;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class CacheFileObject {
	
	private FileHelper fh;
	
	public CacheFileObject(String cachename) {
		fh = new FileHelper(cachename);
	}

	public JSONObject getCachedData(Context context) {
		JSONObject cached = new JSONObject();
		try {
			String raw = fh.readFromFile(context);
			cached = new JSONObject(raw);
		} catch (Exception e) {
			Log.e("CacheError", "getCachedData " + e.getMessage());
		}
		return cached;
	}
	
	public void setCacheData(JSONObject json, Context context) {
		try {
			fh.writeToFile(json.toString(), context);
		} catch (Exception e) {
			Log.e("CacheError", "setCacheData " + e.getMessage());
		}
	}
}
