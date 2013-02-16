package com.awezumTree.buddyolympics.restClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public abstract class HTTPRestTemplate extends AsyncTask {

	private ArrayList<NameValuePair> httpHeaders;
	private ArrayList<NameValuePair> httpParams;
	protected JSONObject jsonBody;
	private AsyncTaskCallback callback;
	protected String url;
	protected DefaultHttpClient httpclient = null;
	protected HttpUriRequest httpMessage = null;

	public void addHttpParam(String name, String value) {
		this.httpParams.add(new BasicNameValuePair(name, value));
	}

	public void addHttpHeader(String name, String value) {
		this.httpHeaders.add(new BasicNameValuePair(name, value));
	}

	public void setJsonBody(Bundle data) {
		Iterator<String> keys = data.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			try {
				jsonBody.put(key, data.get(key));
			} catch (JSONException e) {
				Log.e("LOLCAT", "setJsonBody() - " + e.getMessage());
			}
		}
	}
	
	public void setJsonBody(JSONObject obj) {
		this.jsonBody = obj;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HTTPRestTemplate() {
		this.httpHeaders = new ArrayList<NameValuePair>();
		this.httpParams = new ArrayList<NameValuePair>();
		this.jsonBody = new JSONObject();
	}

	public HTTPRestTemplate(AsyncTaskCallback callback, String url) {
		this();
		this.callback = callback;
		this.url = url;
	}

	public HTTPRestTemplate(String url) {
		this.url = url;
	}

	public abstract void initHttpMethod() throws UnsupportedEncodingException;

	@Override
	protected String doInBackground(Object... urls) {
		return doRestCall();
	}

	public String doRestCall() {
		try {
			httpclient = new DefaultHttpClient();
			initHttpMethod();
			return handleResponse();
		} catch (Exception e) {
			Log.e("LOLCAT", "Error in server communication! " + e.getMessage());
			return "fail";
		} finally {
			if (httpclient != null) {
				httpclient.getConnectionManager().shutdown();
			}

		}
	}

	private String handleResponse() throws ClientProtocolException, IOException {
		ResponseHandler responseHandler = new BasicResponseHandler();

		@SuppressWarnings("unchecked")
		String response = httpclient.execute(httpMessage, responseHandler);
		Log.d("LOLCAT ", "handleResponse() - Response form Henrik: " + response);

		/*
		 * This is only if the return value in execute is not a string! if
		 * (response.getStatusLine().getStatusCode() != 201) { throw new
		 * RuntimeException("Failed : HTTP error code : " +
		 * response.getStatusLine().getStatusCode()); }else { Log.d("LOLCAT",
		 * "BITCHES"); } BufferedReader br = new BufferedReader( new
		 * InputStreamReader((response.getEntity().getContent()))); String
		 * output; System.out.println("Output from Server .... \n"); while
		 * ((output = br.readLine()) != null) { System.out.println(output); }
		 */
		return response;
	}

	// This is called by execute, on the UI thread!
	@Override
	protected void onPostExecute(Object result) {
		if (result != null) {
			Log.d("LOLCAT", "onPostExecute() : " + result.toString());
			if (callback != null) {
				this.callback.callback(result.toString());
			}
		}
	}
}
