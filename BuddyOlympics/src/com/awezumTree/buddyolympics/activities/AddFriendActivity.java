package com.awezumTree.buddyolympics.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.awezumTree.buddyolympics.R;

public class AddFriendActivity extends Activity {
	
	List<Map<String, String>> listmapshits = new ArrayList<Map<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_firend);
		
		initList();
		
		ListView lv = (ListView) findViewById(R.id.friendListView);
		
		SimpleAdapter simpleAdpt = new SimpleAdapter(this, listmapshits, android.R.layout.simple_list_item_1, new String[] {"friend"}, new int[] {android.R.id.text1});

		lv.setAdapter(simpleAdpt);
	}
	
	private void initList() {
		listmapshits.add(createFriendListEntry("Scarlett"));
		listmapshits.add(createFriendListEntry("Bar Rafaeli"));
	}
	
	private HashMap<String, String> createFriendListEntry(String name) {
	    HashMap<String, String> friend = new HashMap<String, String>();
	    friend.put("friend", name);
	     
	    return friend;
	}
}
