package com.awezumTree.buddyolympics.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.awezumTree.buddyolympics.R;
import com.awezumTree.buddyolympics.cache.SimpleRegistry;
import com.awezumTree.buddyolympics.cache.UserCacheRegistry;
import com.awezumTree.buddyolympics.domain.Run;
import com.awezumTree.buddyolympics.domain.User;

public class ShowRunRequests extends Activity {

	 private static final int DIALOG_ALERT = 10;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_run_requests);

		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

		SimpleRegistry reg = SimpleRegistry.getInstance();
		//JSONArray runRequests = (JSONArray) reg
			//	.getObject(HomePageActivity.RUN_REQUESTS);

		//String [] jode = {"Slash", "THe megakill", "John Travz", "Nic Cage"};
		//try {
		User loggedInUser = UserCacheRegistry.get(this);
		for ( Run r : loggedInUser.getNewRuns() ) {
			list.add(putData(r.startTimeString, r.type.getType()));
		}
//		
//			for (int i = 0; i < jode.length;/*runRequests.length();*/ i++) {
//				
//				//JSONObject obj;
//				//obj = new JSONObject(runRequests.get(i).toString());
//				//list.add(putData((String) obj.get("username"), "lol mart!"));
//				list.add(putData(jode[i], "U haz Invitez"));
//			}
//		//} catch (JSONException e) {
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
				  showDialog(DIALOG_ALERT);
			}
		});
	}
	
	 @Override
	  protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case DIALOG_ALERT:
	      // Create out AlterDialog
	      Builder builder = new AlertDialog.Builder(this);
	      builder.setMessage("Do you want to join this run?");
	      builder.setCancelable(true);
	      builder.setPositiveButton("Yes.", new OkOnClickListener());
	      builder.setNegativeButton("No.", new CancelOnClickListener());
	      AlertDialog dialog = builder.create();
	      dialog.show();
	    }
	    return super.onCreateDialog(id);
	  }

	  private final class CancelOnClickListener implements
	      DialogInterface.OnClickListener {
	    public void onClick(DialogInterface dialog, int which) {
	      Toast.makeText(getApplicationContext(), "Activity will continue",
	          Toast.LENGTH_LONG).show();
	    }
	  }

	  private final class OkOnClickListener implements
	      DialogInterface.OnClickListener {
	    public void onClick(DialogInterface dialog, int which) {
	    	ShowRunRequests.this.finish();
	    }
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
