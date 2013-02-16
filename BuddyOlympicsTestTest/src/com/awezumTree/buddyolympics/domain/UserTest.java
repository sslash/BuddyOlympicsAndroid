package com.awezumTree.buddyolympics.domain;

import junit.framework.Assert;
import android.os.Bundle;
import android.test.AndroidTestCase;

public class UserTest extends AndroidTestCase {
	public static final String username = "heaton";
	public static final String email = "not-a-valid-email";
	public static final String password = "asdf";
	private User user;	
	
	
	@Override
	protected void setUp() throws Exception {
		user = createSampleUser();
		super.setUp();
	}



	public void testToString() {
		String expectedJSON = "{"+User.USERNAME+": '"+username+"', "+User.EMAIL+": '"+email+"', "+User.PASSWORD+": '"+password+"'}";
		Assert.assertEquals("\n'"+user.toString()+"'\n'"+expectedJSON+"'\n", expectedJSON, user.toString());
	}
	
	public static User createSampleUser() {
		Bundle configuration = new Bundle();
		configuration.putString(User.USERNAME, username);
		configuration.putString(User.EMAIL, email);
		configuration.putString(User.PASSWORD, password);
		return UserFactory.createUser(configuration);
	}
}
