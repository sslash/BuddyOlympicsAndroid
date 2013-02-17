package com.awezumTree.buddyolympics.restClient;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;

public class RestPutClient extends HTTPRestTemplate {

		public RestPutClient(AsyncTaskCallback runActivity, String url) {
			super(runActivity, url);
		}

		@Override
		public void initHttpMethod() throws UnsupportedEncodingException {
			httpMessage = new HttpPut(url);
			StringEntity se = new StringEntity(jsonBody.toString());
			((HttpPut) httpMessage).setEntity(se);
			httpMessage.setHeader("Accept", "application/json");
			httpMessage.setHeader("Content-type", "application/json");
		}
}
