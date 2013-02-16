package com.awezumTree.buddyolympics.domain;

import java.util.TreeSet;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.awezumTree.buddyolympics.activities.SignUpActivity;

public class UserFactory {
	
	@SuppressLint("NewApi")
	public static User createRunner(Bundle runnerData) {
		User user = new User();
		String username = runnerData.getString(SignUpActivity.USERNAME);
		String email = runnerData.getString(SignUpActivity.EMAIL);
		String password = runnerData.getString(SignUpActivity.PASSWORD);
		
		if ( username != null && !username.isEmpty() ) 
			user.setUsername(username);
		
		if (email != null && !email.isEmpty() ) {
			user.setEmail(email);
		}
		
		if ( password != null && !password.isEmpty()) {
			user.setPassword(password);
		}
		
		user.setFriendlist(new TreeSet<Friend>());
		
		return user;
	}
}
