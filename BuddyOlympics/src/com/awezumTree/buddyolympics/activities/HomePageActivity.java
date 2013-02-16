package com.awezumTree.buddyolympics.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.awezumTree.buddyolympics.R;
import com.awezumTree.buddyolympics.domain.Runner;

public class HomePageActivity extends Activity {



	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);


		// Get the message from the intent
		Intent intent = getIntent();
		Runner user = (Runner) intent.getSerializableExtra("user");
		Log.d("LOLCAT", user.toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_home_page, menu);
		return true;
	}

	public void newRun(View view) {
		Intent intent = new Intent(this, RunActivity.class);
	}

	

}
