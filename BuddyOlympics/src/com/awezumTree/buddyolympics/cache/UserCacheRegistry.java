package com.awezumTree.buddyolympics.cache;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.awezumTree.buddyolympics.domain.User;
import com.awezumTree.buddyolympics.domain.UserFactory;

public class UserCacheRegistry {
	public static final String USER_CACHE_FILE = "user_cache_file";
	
	private CacheFileFetcher fetcher = null;
	private static UserCacheRegistry instance = null;

	private UserCacheRegistry(String cachename) {
		fetcher = new CacheFileFetcher(cachename);
	}
	
	public static User get(String cachename, Context context) {
		if (instance == null) {
			instance = new UserCacheRegistry(cachename);
		}
		return jsonToUser(instance.fetcher.getCachedData(context));
	}

	public static void set(User user, String cachename, Context context) {
		if (instance == null) {
			instance = new UserCacheRegistry(cachename);
		}
		instance.fetcher.setCacheData(userToJson(user), context);
	}
	
	public static User jsonToUser(JSONObject cachedData) {
		Bundle configuration = new Bundle();
		try {
			configuration.putString(User.USERNAME, cachedData.getString(User.USERNAME));
			configuration.putString(User.EMAIL, cachedData.getString(User.EMAIL));
			configuration.putString(User.PASSWORD, cachedData.getString(User.PASSWORD));
		} catch (Exception e) {
			Log.e("UserCacheError", "json to user: " + e.getMessage());
		}
		return UserFactory.createUser(configuration);
	}
	
	public static JSONObject userToJson(User user) {
		JSONObject json = new JSONObject();
		try {
			json.put(User.USERNAME, user.getUsername());
			json.put(User.EMAIL, user.getEmail());
			json.put(User.PASSWORD, user.getPassword());
		} catch (Exception e) {
			Log.e("UserCacheError", "user to json: "+e.getMessage());
		}
		return json;
	}
}
