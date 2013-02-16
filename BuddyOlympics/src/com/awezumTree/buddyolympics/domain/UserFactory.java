package com.awezumTree.buddyolympics.domain;

import java.util.TreeSet;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.awezumTree.buddyolympics.activities.SignUpActivity;

public class UserFactory {
	
	@SuppressLint("NewApi")
	public static User createUser(Bundle runnerData) {
		User user = new User();
		String username = runnerData.getString(User.USERNAME);
		String email = runnerData.getString(User.EMAIL);
		String password = runnerData.getString(User.PASSWORD);
		String runner = runnerData.getString(User.RUNNER);
		
		if ( username != null && !username.isEmpty() ) 
			user.setUsername(username);
		
		if (email != null && !email.isEmpty() ) {
			user.setEmail(email);
		}
		
		if ( password != null && !password.isEmpty()) {
			user.setPassword(password);
		}
		
		if (runner != null && !runner.isEmpty()) {
			user.setRunner(runner);
		}
		
		user.setFriendlist(new TreeSet<Friend>());
		
		return user;
	}
}
