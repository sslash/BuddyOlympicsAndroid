package com.awezumTree.buddyolympics.cache;

import org.json.JSONObject;

import android.content.Context;

public class CacheFileObject {
	
	private FileHelper fh;
	
	public CacheFileObject(String cachename) {
		fh = new FileHelper(cachename);
	}

	public JSONObject getCachedData(Context context) throws CacheException {
		JSONObject cached = new JSONObject();
		try {
			String raw = fh.readFromFile(context);
			cached = new JSONObject(raw);
		} catch (Exception e) {
			throw new CacheException(e);
		}
		return cached;
	}
	
	public void setCacheData(JSONObject json, Context context) throws CacheException {
		try {
			fh.writeToFile(json.toString(), context);
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}
}
