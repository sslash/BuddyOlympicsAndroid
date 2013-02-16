package com.awezumTree.buddyolympics.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.awezumTree.buddyolympics.R;
import com.awezumTree.buddyolympics.domain.User;

public class SignUpActivity extends Activity {
	public final static String BUNDLE = "__BUNDLE__";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sign_up, menu);
		return true;
	}

	public void done(View view) {
		Intent toReturn = this.getIntent();
		toReturn.putExtra(BUNDLE, parseInputAndGetRunnerData());
		this.setResult(RESULT_OK, toReturn);
		this.finish();
	}

	private Bundle parseInputAndGetRunnerData() {
		Bundle data = new Bundle();
		EditText username = (EditText) findViewById(R.id.usernameInput);
		EditText email = (EditText) findViewById(R.id.emailInput);
		EditText password = (EditText) findViewById(R.id.passwordInput);

		data.putString(User.USERNAME, username.getText().toString());
		data.putString(User.EMAIL, email.getText().toString());
		data.putString(User.PASSWORD, password.getText().toString());

		return data;
	}

}
