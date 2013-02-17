package com.awezumTree.buddyolympics.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.awezumTree.buddyolympics.R;
import com.awezumTree.buddyolympics.cache.UserCacheRegistry;
import com.awezumTree.buddyolympics.domain.Run;
import com.awezumTree.buddyolympics.domain.User;
import com.awezumTree.buddyolympics.restClient.AsyncTaskCallback;
import com.awezumTree.buddyolympics.restClient.RestPutClient;

public class ShowRunRequests extends Activity implements AsyncTaskCallback {

	private static final int DIALOG_ALERT = 10;
	private int acceptedRequestIndex = -1;
	private User loggedInUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_run_requests);

		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

		this.loggedInUser = UserCacheRegistry.get(this);
		for (Run r : loggedInUser.getNewRuns()) {
			list.add(putData(r.startTimeString, r.type.getType()));
		}

		ListView listView = (ListView) findViewById(R.id.runRequestsList);
		SimpleAdapter adapter = createSimpleAdapter(list);		
		listView.setAdapter(adapter);

		// HAHA LOL Attributes in callback must be final......
		// Requires JavaScript like code..
		{
			final AsyncTaskCallback that = this;
			final User usr = loggedInUser;
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					acceptedRequestIndex = position;

					String url = new StringBuilder()
					.append(getString(R.string.server_url))
					.append("/runs/")
					.append(usr.getNewRuns().get(acceptedRequestIndex).id)
					.append("/accept/")
					.append(usr.getId())
					.toString();
					
					// Say yes to run request
					Log.d("LOLCAT", "Will pput request: " + url);
					RestPutClient sayYesRequest = new RestPutClient(that, url.toString());
					sayYesRequest.execute();
				}
			});
		}
	}
	
	private SimpleAdapter createSimpleAdapter(ArrayList<Map<String, String>> data) {
		String[] from = { "name", "purpose" };
		int[] to = { android.R.id.text1, android.R.id.text2 };
		SimpleAdapter adapter = new SimpleAdapter(this, data,
				android.R.layout.simple_list_item_2, from, to);
		return adapter;
	}

	private HashMap<String, String> putData(String name, String purpose) {
		HashMap<String, String> item = new HashMap<String, String>();
		item.put("name", name);
		item.put("purpose", purpose);
		return item;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_show_run_requests, menu);
		return true;
	}

	@Override
	public void callback(String res) {
		Log.d("LOLCAT", "after accepgin request : " + res);
		if ( acceptedRequestIndex != -1) {
			Context context = getApplicationContext();
			CharSequence message = "Run Accepted!";
			int duration = Toast.LENGTH_SHORT;			 
			Toast toast = Toast.makeText(context, message, duration);       
			toast.show();
			
			if ( acceptedRequestIndex < loggedInUser.getNewRuns().size()) {
				loggedInUser.getNewRuns().remove(acceptedRequestIndex);
				
				ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
				for (Run r : loggedInUser.getNewRuns()) {
					list.add(putData(r.startTimeString, r.type.getType()));
				}

				ListView listView = (ListView) findViewById(R.id.runRequestsList);
				SimpleAdapter adapter = createSimpleAdapter(list);		
				listView.setAdapter(adapter);
			}
		}		
	}
}
