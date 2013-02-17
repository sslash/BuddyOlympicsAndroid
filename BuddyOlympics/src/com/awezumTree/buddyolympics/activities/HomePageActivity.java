package com.awezumTree.buddyolympics.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.awezumTree.buddyolympics.R;
import com.awezumTree.buddyolympics.cache.SimpleRegistry;
import com.awezumTree.buddyolympics.cache.UserCacheRegistry;
import com.awezumTree.buddyolympics.domain.Runner;
import com.awezumTree.buddyolympics.restClient.AsyncTaskCallback;
import com.awezumTree.buddyolympics.restClient.RestPostClient;

public class HomePageActivity extends Activity{

	// Just so we don't have to call the cache super often
	private Runner loggedInUser;
	
	public static final String RUN_REQUESTS = "__man__bears__wantz__runs__";
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);

		this.setRunner();
		this.getRunRequests();
	}

	private void getRunRequests() {
		//RestPostClient post = new RestPostClient(this,
			//	getString(R.string.server_url)+"/runs/" +loggedInUser.getId());
	}

	private void setRunner() {
		//Intent intent = getIntent();
		//this.loggedInUser = (Runner) intent.getSerializableExtra("user");
		this.loggedInUser = UserCacheRegistry.get(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_home_page, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.new_requests:
	        	openRunRequestsActivity();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void openRunRequestsActivity() {
		Intent intent = new Intent(this, ShowRunRequests.class);		
		startActivity(intent);
	}

	public void newRun(View view) {
		Intent intent = new Intent(this, RunActivity.class);
		startActivity(intent);
	}
	
	public void addFriends(View view) {
		Intent intent = new Intent(this, AddFriendActivity.class);
		startActivity(intent);
	}
	
	public void runRoadTest(View view) {
		Intent intent = new Intent(this, ActuallyRunningActivity.class);
		startActivity(intent);
	}

//	@Override
//	public void callback(String res) {
//
//		try {
//			JSONArray runRequests = new JSONArray(res);
//			SimpleRegistry reg = SimpleRegistry.getInstance();
//			reg.putObject(this.RUN_REQUESTS, reg);
//		} catch (JSONException e) {
//			Log.e("LOLCAT", "callback() : " + e.getMessage());
//		}		
//	}
}
