package com.awezumTree.buddyolympics.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.ViewFlipper;

import com.awezumTree.buddyolympics.R;
import com.awezumTree.buddyolympics.restClient.AsyncTaskCallback;
import com.awezumTree.buddyolympics.restClient.HTTPRestTemplate;
import com.awezumTree.buddyolympics.restClient.RestGetClient;

public class RunActivity extends Activity implements AsyncTaskCallback{

	private float lastX;
	private int stage = 1;
	private ViewFlipper vf;
	private ListView runnersListView;
	private ArrayAdapter<String> runnersAdapter;
	private Button nextButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_run);
		this.setViewFlipper();
		this.setRunTypeList();
		this.fillRunnersList();		
		this.nextButton = (Button) findViewById(R.id.nextRunSelect);
	}
	
	
	private void setViewFlipper() {
		vf = (ViewFlipper) findViewById(R.id.view_flipper);
	}
	
	private void setRunTypeList() {
		ListView listView = (ListView) findViewById(R.id.run_alt_list);

		ArrayList<Map<String, String>> list = buildData();
		String[] from = { "name", "purpose" };
		int[] to = { android.R.id.text1, android.R.id.text2 };

		SimpleAdapter adapter = new SimpleAdapter(this, list,
				android.R.layout.simple_list_item_2, from, to);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				Toast.makeText(getApplicationContext(),
//						"Click ListItem Number " + position, Toast.LENGTH_LONG)
//						.show();
				
				//vf.addView((LinearLayout) findViewById(R.layout.distance_run_layout));
				vf.setDisplayedChild(2+position);
				adjustNextButton();		
			}
		});
	}
	
	public void fillRunnersList() {		
		HTTPRestTemplate get = new RestGetClient(this,"http://192.168.13.102:8080/runners");
		get.execute();
	}

	private ArrayList<Map<String, String>> buildData() {
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list.add(putData("Distance", "Compete to run for a given distance"));
		list.add(putData("Time", "Compete to run for a given time"));
		list.add(putData("Interval", "Compete in an interval lap"));
		list.add(putData("Just run", "Just run 'til you're done"));
		return list;
	}

	private HashMap<String, String> putData(String name, String purpose) {
		HashMap<String, String> item = new HashMap<String, String>();
		item.put("name", name);
		item.put("purpose", purpose);
		return item;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_run, menu);
		return true;
	}
	
	public void nextPage(View view) {
		this.adjustNextButton();
		
	}
	
	
	private void adjustNextButton() {

		Log.d("LOLCAT" , "Now shoing: " + vf.getDisplayedChild() + ", count: " + vf.getChildCount());
		if ( stage == 1 || stage == 3) {
			nextButton.setVisibility(View.INVISIBLE);
			
			if ( stage == 3) {
				Log.d("LOLCAT", "WIll set list thing");
				// 
				
				vf.setDisplayedChild(1);
				Log.d("LOLCAT" , "id: " + vf.getChildAt(0).getId());
				Log.d("LOLCAT" , "id: " + vf.getChildAt(1).getId());
				Log.d("LOLCAT" , "id: " + vf.getChildAt(2).getId());
				Log.d("LOLCAT" , "id: " + vf.getChildAt(3).getId());
				Log.d("LOLCAT" , "id: " + vf.getChildAt(4).getId());
				
				
			}
		} else if (stage == 2 ) {
			nextButton.setVisibility(View.VISIBLE);
		}
		Log.d("LOLCAT", "stage= " + stage);
		stage ++;
		vf.showNext();
	}
	
	
	public void doneRunCreate(View view) {
		SparseBooleanArray checked = this.runnersListView.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checked.valueAt(i))
                selectedItems.add(this.runnersAdapter.getItem(position));
        }
 
        String[] outputStrArr = new String[selectedItems.size()];
 
        for (int i = 0; i < selectedItems.size(); i++) {
           Log.d("LOLCAT", "du har: " + selectedItems.get(i));
        }
	}
/*
	@Override
	public boolean onTouchEvent(MotionEvent touchevent) {
		switch (touchevent.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			lastX = touchevent.getX();
			break;
		}
		case MotionEvent.ACTION_UP: {
			float currentX = touchevent.getX();
			if (lastX < currentX) {
				if (vf.getDisplayedChild() == 0)
					break;
				vf.setInAnimation(this, R.anim.in_from_left);
				vf.setOutAnimation(this, R.anim.out_to_right);
				vf.showPrevious();
			}
			if (lastX > currentX) {
				if (vf.getDisplayedChild() == 1)
					break;
				vf.setInAnimation(this, R.anim.in_from_right);
				vf.setOutAnimation(this, R.anim.out_to_left);
				vf.showNext();
			}
			break;
		}
		}
		return false;
	}*/


	@Override
	public void callback(String res) {
		JSONArray json;
		try {
			json = new JSONArray(res);
			
			
			JSONObject obj = new JSONObject(json.get(0).toString());
			Log.d("LOLCAT" , "object now : " + obj.toString());
		
		Log.d("LOLCAT", "callbacken: " + res);
		String[] sports = {obj.getString("username"), "teen", "sex", "dap", "fapp", "crap"};
		
		this.runnersAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, sports);
		this.runnersListView = (ListView) findViewById(R.id.runnersList);
		this.runnersListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		this.runnersListView.setAdapter(this.runnersAdapter);
		
		} catch (JSONException e) {
			Log.d("LOLCAT", "json parse  : " + e.getMessage());
		}
	
		
	}

}
