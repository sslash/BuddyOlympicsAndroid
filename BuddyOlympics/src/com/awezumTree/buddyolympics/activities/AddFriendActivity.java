package com.awezumTree.buddyolympics.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.awezumTree.buddyolympics.R;
import com.awezumTree.buddyolympics.cache.UserCacheRegistry;
import com.awezumTree.buddyolympics.domain.Friend;
import com.awezumTree.buddyolympics.domain.User;

public class AddFriendActivity extends Activity {
	
	List<Map<String, String>> friendlist = new ArrayList<Map<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_firend);
		
		User user = UserCacheRegistry.get(UserCacheRegistry.USER_CACHE_FILE, this);
		
		for (Friend friend : user.getFriendlist()) {
			friendlist.add(createFriendListEntry(friend.getUsername()));
		}
		
		ListView lv = (ListView) findViewById(R.id.friendListView);
		
		SimpleAdapter simpleAdpt = new SimpleAdapter(this, friendlist, android.R.layout.simple_list_item_1, new String[] {"friend"}, new int[] {android.R.id.text1});

		lv.setAdapter(simpleAdpt);
	}
	
	private HashMap<String, String> createFriendListEntry(String name) {
	    HashMap<String, String> friend = new HashMap<String, String>();
	    friend.put("friend", name);
	    return friend;
	}
}
