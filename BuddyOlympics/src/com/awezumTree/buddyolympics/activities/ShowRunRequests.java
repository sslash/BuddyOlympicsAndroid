package com.awezumTree.buddyolympics.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.awezumTree.buddyolympics.R;
import com.awezumTree.buddyolympics.cache.SimpleRegistry;

public class ShowRunRequests extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_run_requests);

		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

		SimpleRegistry reg = SimpleRegistry.getInstance();
		JSONArray runRequests = (JSONArray) reg
				.getObject(HomePageActivity.RUN_REQUESTS);

		String [] jode = {"Slash", "THe megakill", "John Travz", "Nic Cage"};
		//try {
			for (int i = 0; i < jode.length;/*runRequests.length();*/ i++) {
				//JSONObject obj;
				//obj = new JSONObject(runRequests.get(i).toString());
				//list.add(putData((String) obj.get("username"), "lol mart!"));
				list.add(putData(jode[i], "U haz Invitez"));
			}
		//} catch (JSONException e) {
			//Log.d("LOLCAT", "baedet paa jason parserer funksjonalitet! " + e.getMessage());
		//}
		
		ListView listView = (ListView) findViewById(R.id.runRequestsList);
		String[] from = { "name", "purpose" };
		int[] to = { android.R.id.text1, android.R.id.text2 };

		SimpleAdapter adapter = new SimpleAdapter(this, list,
				android.R.layout.simple_list_item_2, from, to);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				// 1. Instantiate an AlertDialog.Builder with its constructor
				AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

				// 2. Chain together various setter methods to set the dialog characteristics
				builder.setMessage("U have brown skin color.")
				       .setTitle("Which is niez");

				// 3. Get the AlertDialog from create()
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});
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

}
