package com.awezumTree.buddyolympics.restClient;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;


// Uses AsyncTask to create a task away from the main UI thread.
public class RestPostClient extends HTTPRestTemplate {

	public RestPostClient(AsyncTaskCallback runActivity, String url) {
		super(runActivity, url);
	}
	
	public RestPostClient(String url) {
		super(url);
	}

	@Override
	public void initHttpMethod() throws UnsupportedEncodingException {
		httpMessage = new HttpPost(url);
		StringEntity se = new StringEntity(jsonBody.toString());
		((HttpPost) httpMessage).setEntity(se);
		httpMessage.setHeader("Accept", "application/json");
		httpMessage.setHeader("Content-type", "application/json");
	}
}
