package com.awezumTree.buddyolympics.cache;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.awezumTree.buddyolympics.domain.Run;
import com.awezumTree.buddyolympics.domain.User;
import com.awezumTree.buddyolympics.domain.UserFactory;
import com.awezumTree.buddyolympics.schemas.RunnersSchema;

public class UserCacheRegistry {
	public static final String USER_CACHE_FILE = "user_cache_file";
	
	private CacheFileFetcher fetcher = null;
	private static UserCacheRegistry instance = null;

	private UserCacheRegistry(String cachename) {
		fetcher = new CacheFileFetcher(cachename);
	}
	
	public static User get(Context context) {
		if (instance == null) {
			instance = new UserCacheRegistry(UserCacheRegistry.USER_CACHE_FILE);
		}
		return jsonToUser(instance.fetcher.getCachedData(context));
	}

	public static void set(User user, Context context) {
		if (instance == null) {
			instance = new UserCacheRegistry(UserCacheRegistry.USER_CACHE_FILE);
		}
		instance.fetcher.setCacheData(userToJson(user), context);
	}
	
	public static void set(JSONObject user, Context context) {
		if (instance == null) {
			instance = new UserCacheRegistry(UserCacheRegistry.USER_CACHE_FILE);
		}
		instance.fetcher.setCacheData(user, context);
	}
	
	
	public static User jsonToUser(JSONObject cachedData) {
		Bundle configuration = new Bundle();
		try {
			configuration.putString(RunnersSchema.ID, cachedData.getString(RunnersSchema.ID));
			configuration.putString(RunnersSchema.USERNAME, cachedData.getString(RunnersSchema.USERNAME));
			configuration.putString(RunnersSchema.EMAIL, cachedData.getString(RunnersSchema.EMAIL));
			configuration.putString(RunnersSchema.PASSWORD, cachedData.getString(RunnersSchema.PASSWORD));
			User user = UserFactory.createUser(configuration);
			
			// Set runs for user
			String newRunsStr = cachedData.getString(RunnersSchema.NEW_RUNS);
			JSONArray newRuns= new JSONArray(newRunsStr);
			int len = newRuns.length();
			List <Run> newRunsArrayList = new ArrayList<Run>();
			
			for(int i = 0; i < len; i++) {
				JSONObject runJSON = (JSONObject) newRuns.get(i);
				Run r = new Run();
				r.setAttributesFromJSON(runJSON);
				newRunsArrayList.add(r);
			}
			
			user.setNewRuns(newRunsArrayList);
			return user;
			
		} catch (Exception e) {
			Log.e("UserCacheError", "json to user: " + e.getMessage());
			return null;
		}
		
	}
	
	public static JSONObject userToJson(User user) {
		JSONObject json = new JSONObject();
		try {
			json.put(RunnersSchema.USERNAME, user.getUsername());
			json.put(RunnersSchema.EMAIL, user.getEmail());
			json.put(RunnersSchema.PASSWORD, user.getPassword());
		} catch (Exception e) {
			Log.e("UserCacheError", "user to json: "+e.getMessage());
		}
		return json;
	}
}
