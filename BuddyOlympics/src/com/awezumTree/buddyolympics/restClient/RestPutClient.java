package com.awezumTree.buddyolympics.restClient;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpPut;

public class RestPutClient extends HTTPRestTemplate {

		public RestPutClient(AsyncTaskCallback runActivity, String url) {
			super(runActivity, url);
		}

		@Override
		public void initHttpMethod() throws UnsupportedEncodingException {
			httpMessage = new HttpPut(url);
			httpMessage.setHeader("Accept", "application/json");
			httpMessage.setHeader("Content-type", "application/json");
		}
}
