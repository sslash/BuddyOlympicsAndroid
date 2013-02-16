package com.awezumTree.buddyolympics.cache;

import org.json.JSONObject;

import android.content.Context;

public class UserCacheRegistry {
	
	private CacheFileFetcher fetcher = null;
	private static UserCacheRegistry instance = null;

	private UserCacheRegistry(String cachename) {
		fetcher = new CacheFileFetcher(cachename);
	}
	
	public static JSONObject get(String cachename, Context context) {
		if (instance == null) {
			instance = new UserCacheRegistry(cachename);
		}
		return instance.fetcher.getCachedData(context);
	}
	
	public static void set(JSONObject value, String cachename, Context context) {
		if (instance == null) {
			instance = new UserCacheRegistry(cachename);
		}
		instance.fetcher.setCacheData(value, context);
	}
}
