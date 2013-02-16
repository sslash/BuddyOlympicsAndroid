package com.awezumTree.buddyolympics.domain;

import com.awezumTree.buddyolympics.activities.SignUpActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

public class RunnerFactory {
	
	@SuppressLint("NewApi")
	public static Runner createRunner(Bundle runnerData) {
		Runner runner = new Runner();
		String username = runnerData.getString(SignUpActivity.USERNAME);
		String email = runnerData.getString(SignUpActivity.EMAIL);
		String password = runnerData.getString(SignUpActivity.PASSWORD);
		
		if ( username != null && !username.isEmpty() ) 
			runner.setUsername(username);
		
		if (email != null && !email.isEmpty() ) {
			runner.setEmail(email);
		}
		
		if ( password != null && !password.isEmpty()) {
			runner.setPassword(password);
		}	
		
		return runner;
	}
}
