package com.awezumTree.buddyolympics.domain;

import junit.framework.Assert;
import android.os.Bundle;
import android.test.AndroidTestCase;

import com.awezumTree.buddyolympics.schemas.RunnersSchema;

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
		String expectedJSON = "{"+RunnersSchema.USERNAME+": '"+username+"', "+RunnersSchema.EMAIL+": '"+email+"', "+RunnersSchema.PASSWORD+": '"+password+"'}";
		Assert.assertEquals("\n'"+user.toString()+"'\n'"+expectedJSON+"'\n", expectedJSON, user.toString());
	}
	
	public static User createSampleUser() {
		Bundle configuration = new Bundle();
		configuration.putString(RunnersSchema.USERNAME, username);
		configuration.putString(RunnersSchema.EMAIL, email);
		configuration.putString(RunnersSchema.PASSWORD, password);
		return UserFactory.createUser(configuration);
	}
}
