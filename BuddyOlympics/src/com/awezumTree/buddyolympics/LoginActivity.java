package com.awezumTree.buddyolympics;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.awezumTree.buddyolympics.activities.ActuallyRunningActivity;
import com.awezumTree.buddyolympics.activities.AuthenticateActivity;
import com.awezumTree.buddyolympics.activities.HomePageActivity;
import com.awezumTree.buddyolympics.activities.RunActivity;
import com.awezumTree.buddyolympics.activities.SignUpActivity;
import com.awezumTree.buddyolympics.cache.UserCacheRegistry;
import com.awezumTree.buddyolympics.domain.User;
import com.awezumTree.buddyolympics.domain.UserFactory;
import com.awezumTree.buddyolympics.restClient.AsyncTaskCallback;
import com.awezumTree.buddyolympics.restClient.RestPostClient;

public class LoginActivity extends Activity implements AsyncTaskCallback{

	public static final int SIGN_UP_ACTIVITY = 1;
	public static final int LOGIN_ACTIVITY = 2;
	
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	/**
	 * Called when the user clicks the signup button
	 * 
	 * Be public Have a void return value Have a View as the only parameter
	 * (this will be the View that was clicked)
	 * 
	 */
	public void signUp(View view) {
		Intent intent = new Intent(this, SignUpActivity.class);
		startActivityForResult(intent, SIGN_UP_ACTIVITY);
	}
	
	public void runTest(View view) {
		Intent intent = new Intent(this, RunActivity.class);
		startActivity(intent);
	}
	
	public void actuallyRunning(View view) {
		Intent intent = new Intent(this, ActuallyRunningActivity.class);
		startActivity(intent);
	}
	


	public void login(View view) {
		Intent intent = new Intent(this, AuthenticateActivity.class);
		startActivityForResult(intent, LOGIN_ACTIVITY);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == SIGN_UP_ACTIVITY) {
			if (resultCode == RESULT_OK) {
				this.saveRunnerAndLogin(data.getExtras());
			}
		}else if(requestCode == LOGIN_ACTIVITY ) {
			if (resultCode == RESULT_OK ) {
				this.checkAuthenticationResAndMaybeLogin(data.getExtras());
			}
		}
	}

	private void checkAuthenticationResAndMaybeLogin(Bundle extras) {
		String authData = (String) extras.get(SignUpActivity.BUNDLE);
		if ( authData != null ) {
			JSONObject usr = this.tryTurnAuthenticationDataIntoJSONObject(authData);
			if ( usr != null) {
				this.saveUserToCache(usr);
				Log.d("LOLCAT", "auth success, which you alraeady know cause u r lol and cool");
				Log.d("LOLCAT", "user: " + UserCacheRegistry.get(this).toString());
				doLogIn();	
			}		
		} else {
			Log.d("LOLCAT", "auth failed.. You probably already know though..");
		}		
	}
	
	private void saveUserToCache(JSONObject usr){
		UserCacheRegistry.set(usr, this);
	}

	private JSONObject tryTurnAuthenticationDataIntoJSONObject(String authData) {
		try {
			JSONObject userAsJSON = new JSONObject(authData);
			Log.d("LOLCAT", "JSON str is now: " + authData.toString());
			return userAsJSON;
		} catch (JSONException e) {
			Log.e("LOLCAT","tryTurnAuthenticationDataIntoJSONObject() - " + e.getMessage());
			return null;
		}		
	}

	private void saveRunnerAndLogin(Bundle runnerData) {
		if (this.checkInternetConnection()) {
			this.postRunnerToServer(runnerData);		
		} else {
			Toast.makeText(getApplicationContext(),
					R.string.connect_to_internet, Toast.LENGTH_LONG).show();
		}
	}

	private void postRunnerToServer(Bundle inputData) {
		RestPostClient post = new RestPostClient(this,getString(R.string.server_url)+"/runners" );
		Bundle runnerData = (Bundle) inputData.get(SignUpActivity.BUNDLE); 
		this.user = UserFactory.createUser(runnerData);
		post.setJsonBody(runnerData);			
		post.execute();			
	}
	
	

	private boolean checkInternetConnection() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnected();
	}

	@Override
	public void callback(String res) {
		if ( !"ok".equals(res) ){
			Toast.makeText(getApplicationContext(),
					R.string.reg_success, Toast.LENGTH_LONG).show();
			if ( user != null ){
				UserCacheRegistry.set(user, this);
				doLogIn();
			}else {
				Log.e("LOLCAT", "USER WAS NULL BIETCH");
			}
		} else {
			Log.d("LOLCAT", "NGR " + res);
		}
		
	}

	private void doLogIn() {
		//Log.d("LOL", "Will log in user! " + user.toString());
		Intent intent = new Intent(this, HomePageActivity.class);
	    //intent.putExtra("user", user);
	    startActivity(intent);
	}
}
