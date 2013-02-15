package com.awezumTree.buddyolympics;

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

import com.awezumTree.buddyolympics.activities.SignUpActivity;
import com.awezumTree.buddyolympics.restClient.AsyncTaskCallback;
import com.awezumTree.buddyolympics.restClient.RestClient;

public class LoginActivity extends Activity implements AsyncTaskCallback{

	public static final int SIGN_UP_ACTIVITY = 1;

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

		// An intent provides runtime binding between two components
		Intent intent = new Intent(this, SignUpActivity.class);
		startActivityForResult(intent, SIGN_UP_ACTIVITY);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == SIGN_UP_ACTIVITY) {
			if (resultCode == RESULT_OK) {
				this.saveRunnerAndLogin(data.getExtras());
			}
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

	private void postRunnerToServer(Bundle runnerData) {
		RestClient post = new RestClient(this);
		post.setJsonBody((Bundle)runnerData.get(SignUpActivity.BUNDLE));			
		post.execute("http://192.168.13.101:8080/runners");			
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
			
		} else {
			Log.d("LOLCAT", "NGR " + res);
		}
		
	}
}
