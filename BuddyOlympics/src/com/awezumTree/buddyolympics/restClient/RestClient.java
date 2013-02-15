package com.awezumTree.buddyolympics.restClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

// Uses AsyncTask to create a task away from the main UI thread.
public class RestClient extends AsyncTask {
	
	private ArrayList <NameValuePair> httpHeaders;
	private ArrayList <NameValuePair> httpParams;
	private JSONObject jsonBody;
	private AsyncTaskCallback callback;
	
	public void addHttpParam(String name, String value) {
		this.httpParams.add(new BasicNameValuePair(name,value));
	}
	
	public void addHttpHeader(String name, String value) {
		this.httpHeaders.add(new BasicNameValuePair(name,value));
	}
	
	public void setJsonBody(Bundle data) {
		Iterator <String> keys = data.keySet().iterator();
		while(keys.hasNext()) {
			String key = keys.next();
			try {
				jsonBody.put(key, data.get(key));
			} catch (JSONException e) {
				Log.e("LOLCAT", "setJsonBody() - " + e.getMessage());
			}
		}
	}
	
	
	public RestClient() {
		this.httpHeaders = new ArrayList<NameValuePair>();
		this.httpParams = new ArrayList<NameValuePair>();
		this.jsonBody = new JSONObject();
	}

	public RestClient(AsyncTaskCallback callback) {
		this();
		this.callback = callback;
	}

	// Called by execute!
	@Override
	protected String doInBackground(Object... urls) {
		try {
			return doPost((String) urls[0]);
		} catch (IOException e) {
			Log.e("Unable to retrieve url! ", e.getMessage());
			return "Unable to retrieve url! " + e.getMessage();
		}
	}

	// Invoked on the UI thread
	@Override
	protected void onPostExecute(Object result) {
		Log.d("LOLCAT" , "hei sap: " + result.toString() );
		if ( callback != null ) {
			this.callback.callback(result.toString());
		}
	}

	private String doPost(String url) throws IOException {
		DefaultHttpClient httpclient = null;
		try {
			httpclient = new DefaultHttpClient();
			HttpPost httpost = new HttpPost(url);
			StringEntity se = new StringEntity(jsonBody.toString());
			httpost.setEntity(se);
			httpost.setHeader("Accept", "application/json");
			httpost.setHeader("Content-type", "application/json");
			
			ResponseHandler responseHandler = new BasicResponseHandler();
			
			@SuppressWarnings("unchecked")
			String response = httpclient.execute(httpost, responseHandler);
			Log.d("LOLCAT ", response );
			/*
			if (response.getStatusLine().getStatusCode() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatusLine().getStatusCode());
			}else {
				Log.d("LOLCAT", "BITCHES");
			}
	 
			BufferedReader br = new BufferedReader(
	                        new InputStreamReader((response.getEntity().getContent())));
	 
	 
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			
	*/
			return response;
	 
		} finally {
			if (httpclient != null) {
				httpclient.getConnectionManager().shutdown();
			}
			
		}
	}

}
