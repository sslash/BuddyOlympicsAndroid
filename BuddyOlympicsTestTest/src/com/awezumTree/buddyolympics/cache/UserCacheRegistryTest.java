package com.awezumTree.buddyolympics.cache;


import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;

import android.test.AndroidTestCase;

import com.awezumTree.buddyolympics.domain.User;
import com.awezumTree.buddyolympics.domain.UserTest;
import com.awezumTree.buddyolympics.schemas.RunnersSchema;

public class UserCacheRegistryTest extends AndroidTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testUserToJson() throws JSONException {
		JSONObject actual = UserCacheRegistry.userToJson(UserTest.createSampleUser());
		JSONObject expected = createSampleJSON();
		Assert.assertEquals(expected.toString(), actual.toString());
	}
	
	public void testJsonToUser() throws JSONException {
		User actual = UserCacheRegistry.jsonToUser(createSampleJSON());
		User expected = UserTest.createSampleUser();
		Assert.assertEquals(expected.toString(), actual.toString());
	}
	
	public static JSONObject createSampleJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(RunnersSchema.USERNAME, UserTest.username);
		json.put(RunnersSchema.EMAIL, UserTest.email);
		json.put(RunnersSchema.PASSWORD, UserTest.password);
		return json;
	}

}
