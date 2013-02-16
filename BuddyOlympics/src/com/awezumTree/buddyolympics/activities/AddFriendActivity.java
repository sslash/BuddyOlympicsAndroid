package com.awezumTree.buddyolympics.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.awezumTree.buddyolympics.R;
import com.awezumTree.buddyolympics.cache.UserCacheRegistry;
import com.awezumTree.buddyolympics.domain.Friend;
import com.awezumTree.buddyolympics.domain.FriendFactory;
import com.awezumTree.buddyolympics.domain.User;
import com.awezumTree.buddyolympics.restClient.AsyncTaskCallback;
import com.awezumTree.buddyolympics.restClient.RestGetClient;

public class AddFriendActivity extends Activity implements AsyncTaskCallback{
	
	List<Map<String, String>> runnerlist = new ArrayList<Map<String, String>>();
	Set<Friend> friendlist = new HashSet<Friend>();
	User user;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_friend);
		
		User user = UserCacheRegistry.get(this);
		friendlist = user.getFriendlist();

		RestGetClient get = new RestGetClient(this, R.string.server_url+"/runners");
		get.execute();

	}
	
	private HashMap<String, String> createFriendListEntry(String name) {
	    HashMap<String, String> friend = new HashMap<String, String>();
	    friend.put("friend", name);
	    return friend;
	}

	@Override
	public void callback(String res) {
		JSONArray json = new JSONArray();
		try {
			json = new JSONArray(res);
			for (int i = 0; i < json.length(); i++) {
				JSONObject runner = (JSONObject) json.get(i);
				Log.d("TIGERCAT", runner.getString(User.USERNAME));
				runnerlist.add(createFriendListEntry(runner.getString(User.USERNAME)));
			}
		} catch (JSONException e) {
			Log.e("JsonError", "get runners callback - " + e.getMessage());
		}
		
		
		final ListView lv = (ListView) findViewById(R.id.friendListView);
		
		SimpleAdapter simpleAdpt = new SimpleAdapter(this, runnerlist, android.R.layout.simple_list_item_1, new String[] {"friend"}, new int[] {android.R.id.text1});

		lv.setClickable(true);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				HashMap<String, String> entry = (HashMap) lv.getItemAtPosition(position);
				String newFriend = entry.get("friend");

				
				for (Friend friend : friendlist) {
					if (!friend.getUsername().equals(newFriend)) {
						Log.d("TIGERCAT", "GOTZ NEW FRIEND: " + newFriend);
						friendlist.add(FriendFactory.createFriend(newFriend));
						user.setFriendlist(friendlist);
					}
				}
			}
			
		});
		
		lv.setAdapter(simpleAdpt);
	}

}
