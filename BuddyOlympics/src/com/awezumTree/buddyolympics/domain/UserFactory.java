package com.awezumTree.buddyolympics.domain;

import java.util.TreeSet;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.awezumTree.buddyolympics.schemas.ParticipantSchema;
import com.awezumTree.buddyolympics.schemas.RunnersSchema;

public class UserFactory {
	
	@SuppressLint("NewApi")
	public static User createUser(Bundle runnerData) {
		User user = new User();
		String username = runnerData.getString(RunnersSchema.USERNAME);
		String email = runnerData.getString(RunnersSchema.EMAIL);
		String password = runnerData.getString(RunnersSchema.PASSWORD);
		String runner = runnerData.getString(ParticipantSchema.RUNNER);
		
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
