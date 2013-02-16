package com.awezumTree.buddyolympics.cache;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class CacheFileFetcher {
	
	private FileHelper fh;
	private JSONObject data = null;
	
	public CacheFileFetcher(String cachename) {
		fh = new FileHelper(cachename);
	}
	
	public JSONObject getCachedData(Context context) {
		if (data == null) {
			data = getPersistedCacheData(context);
		}
		return data;
	}
	
	public void setCacheData(JSONObject value, Context context) {
		data = value;
		persistCacheData(context);
	}

	private JSONObject getPersistedCacheData(Context context) {
		try {
			String raw = fh.readFromFile(context);
			data = new JSONObject(raw);
		} catch (Exception e) {
			Log.e("CacheError", "getCachedData " + e.getMessage());
		}
		return data;
	}
	
	private void persistCacheData(Context context) {
		try {
			fh.writeToFile(data.toString(), context);
		} catch (Exception e) {
			Log.e("CacheError", "setCacheData " + e.getMessage());
		}
	}
}
