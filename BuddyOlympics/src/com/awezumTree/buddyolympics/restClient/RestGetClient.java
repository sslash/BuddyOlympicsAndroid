package com.awezumTree.buddyolympics.restClient;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpGet;

public class RestGetClient extends HTTPRestTemplate {

	public RestGetClient(AsyncTaskCallback runActivity, String url) {
		super(runActivity, url);
	}

	@Override
	public void initHttpMethod() throws UnsupportedEncodingException {
		httpMessage = new HttpGet(url);
		httpMessage.setHeader("Accept", "application/json");
		//httpMessage.setHeader("Content-type", "application/json");
	}

}
