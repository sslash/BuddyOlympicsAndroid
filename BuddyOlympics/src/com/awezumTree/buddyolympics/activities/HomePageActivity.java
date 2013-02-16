package com.awezumTree.buddyolympics.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.awezumTree.buddyolympics.R;
import com.awezumTree.buddyolympics.domain.Runner;
import com.awezumTree.buddyolympics.domain.User;
import com.awezumTree.buddyolympics.restClient.AsyncTaskCallback;
import com.awezumTree.buddyolympics.restClient.RestGetClient;
import com.awezumTree.buddyolympics.restClient.RestPostClient;

public class HomePageActivity extends Activity implements AsyncTaskCallback{

	private Runner loggedInUser;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);


		this.setRunner();
		this.getRunRequests();
	}

	private void getRunRequests() {
		RestPostClient post = new RestPostClient(this,
				"http://192.168.13.102:8080/runs/" +loggedInUser.getId());
		
		
	}

	private void setRunner() {
		Intent intent = getIntent();
		this.loggedInUser = (Runner) intent.getSerializableExtra("user");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_home_page, menu);
		return true;
	}

	public void newRun(View view) {
		Intent intent = new Intent(this, RunActivity.class);
		startActivity(intent);
	}
	
	public void addFriends(View view) {
		Intent intent = new Intent(this, AddFriendActivity.class);
		startActivity(intent);
	}

	@Override
	public void callback(String res) {
		// TODO Auto-generated method stub
		
	}

	

}
