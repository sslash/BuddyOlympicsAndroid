package com.awezumTree.buddyolympics.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.awezumTree.buddyolympics.R;
import com.awezumTree.buddyolympics.cache.UserCacheRegistry;
import com.awezumTree.buddyolympics.domain.User;
import com.awezumTree.buddyolympics.restClient.AsyncTaskCallback;
import com.awezumTree.buddyolympics.restClient.RestPostClient;

@SuppressLint("NewApi")
public class AuthenticateActivity extends Activity implements AsyncTaskCallback {

	private Bundle authData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authenticate);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_authenticat, menu);
		return true;
	}

	public void login(View view) {
		EditText username = (EditText) findViewById(R.id.usernameAuthInput);
		EditText password = (EditText) findViewById(R.id.passwordAuthInput);

		doAuthenticate(username.getText().toString(), password.getText()
				.toString());
	}

	private boolean doAuthenticate(String u, String p) {
		if (u == null || u.isEmpty() || p == null || p.isEmpty()){
			//TODO: Give r-tard msg to user!
			return false;
		}

		this.authData = new Bundle();
		this.authData.putString(User.USERNAME, u);
		this.authData.putString(User.PASSWORD, p);
		RestPostClient post = new RestPostClient(this,getString(R.string.server_url)+"/login");
		Log.d("LOLCAT", "u + p: " + u + ",'" + p+"'");
		post.setJsonBody(this.authData);
		post.execute();
		return true;
	}

	@Override
	public void callback(String res) {
		Intent toReturn = this.getIntent();
		Log.d("LOLCAT", "RES FROM HENRIKZ: " + res);
		
		if ("fail".equals(res)) {
			Log.e("LOLCAT", "AUTH FAILED! YOU CUNT.");
			Toast.makeText(getApplicationContext(), "Authentication failed. You have no soul",
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(getApplicationContext(), R.string.reg_success,
					Toast.LENGTH_LONG).show();
			toReturn.putExtra(SignUpActivity.BUNDLE, this.authData);
		}
		try {
			JSONObject usr = new JSONObject(res);
			UserCacheRegistry.set(usr, this);
		} catch (JSONException e) {
			Log.e("PERSISTANCE", "Could not persist shit - " + e.getMessage());
		}
		
		this.setResult(RESULT_OK, toReturn);
		this.finish();
	}
}
