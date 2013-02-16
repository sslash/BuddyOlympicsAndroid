package com.awezumTree.buddyolympics.activities;

import java.util.ArrayList;
import java.util.Date;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.awezumTree.buddyolympics.R;
import com.awezumTree.buddyolympics.domain.Run;
import com.awezumTree.buddyolympics.domain.Runner;
import com.awezumTree.buddyolympics.restClient.AsyncTaskCallback;
import com.awezumTree.buddyolympics.restClient.HTTPRestTemplate;
import com.awezumTree.buddyolympics.restClient.RestGetClient;
import com.awezumTree.buddyolympics.restClient.RestPostClient;

public class RunActivity extends Activity implements AsyncTaskCallback {

	private float lastX;
	private int stage = 1;
	private int runType = -1;
	private ViewFlipper vf;
	private ListView runnersListView;
	private ArrayAdapter<String> runnersAdapter;
	private Button nextButton;
	private Button doneButton;
	private ArrayList<Runner> selectedItems;
	private JSONArray allRunners;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_run);
		this.setViewFlipper();
		this.setRunTypeList();
		this.fillRunnersList();
		this.nextButton = (Button) findViewById(R.id.nextRunSelect);
		this.doneButton = (Button) findViewById(R.id.donebutton);
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
				runType = position;
				vf.setDisplayedChild(2 + position); // + 2, because the first 2 static choices
				adjustNextButton();
			}
		});
	}

	public void fillRunnersList() {
		HTTPRestTemplate get = new RestGetClient(this,
				"http://192.168.13.102:8080/runners");
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

	/**
	 * This is a game. One is allowed to code weird. Flags and shit is ok.
	 */
	private void adjustNextButton() {
		
		if (stage == 1 || stage == 3) {
			nextButton.setVisibility(View.INVISIBLE);

			if (stage == 3) {
				vf.setDisplayedChild(4);
				doneButton.setVisibility(View.VISIBLE);
				return;
			}

		} else if (stage == 2) {
			nextButton.setVisibility(View.VISIBLE);
			stage++;
			return;
		}
		
		stage++;
		vf.showNext();
	}

	public void doneRunCreate(View view) {
		SparseBooleanArray checked = this.runnersListView
				.getCheckedItemPositions();
		this.setCheckedRunners(checked);
		persistRunObject();
	}

	/*
	 * @Override public boolean onTouchEvent(MotionEvent touchevent) { switch
	 * (touchevent.getAction()) { case MotionEvent.ACTION_DOWN: { lastX =
	 * touchevent.getX(); break; } case MotionEvent.ACTION_UP: { float currentX
	 * = touchevent.getX(); if (lastX < currentX) { if (vf.getDisplayedChild()
	 * == 0) break; vf.setInAnimation(this, R.anim.in_from_left);
	 * vf.setOutAnimation(this, R.anim.out_to_right); vf.showPrevious(); } if
	 * (lastX > currentX) { if (vf.getDisplayedChild() == 1) break;
	 * vf.setInAnimation(this, R.anim.in_from_right); vf.setOutAnimation(this,
	 * R.anim.out_to_left); vf.showNext(); } break; } } return false; }
	 */

	private void setCheckedRunners(SparseBooleanArray checked) {
		this.selectedItems = new ArrayList<Runner>();
		for (int i = 0; i < checked.size(); i++) {
			// Item position in adapter
			int position = checked.keyAt(i);
			// Add sport if it is checked i.e.) == TRUE!
			if (checked.valueAt(i)) {
				try {
					JSONObject obj = new JSONObject(allRunners.get(position)
							.toString());
					selectedItems.add(new Runner((String) obj.get("_id")));
				} catch (JSONException e) {
					Log.e("LOLCAT","setCheckedRunners() - "+e.getMessage() );
				}
			}
		}
		
	}

	private void persistRunObject() {
		RestPostClient post = new RestPostClient("http://192.168.13.102:8080/runs");
		Run r = new Run();
		r.type = r.new Type();
		r.participants = this.selectedItems;
		TimePicker time = (TimePicker) findViewById(R.id.timePicker1);
		int hour = time.getCurrentHour();
		int minutes = time.getCurrentMinute();
		r.startTime = new Date();
		r.startTime.setHours(hour);
		r.startTime.setMinutes(minutes);
		this.setRunTypeInformation(r);		

		JSONObject obj;
		try {
			obj = new JSONObject(r.toJsonString());
			post.setJsonBody(obj);
		} catch (JSONException e) {
			Log.d("LOLCAT", "error parse jsON: " + e.getMessage());
		}

		post.execute();
		Toast.makeText(getApplicationContext(),
		"Niez. New Run. Niez.", Toast.LENGTH_LONG)
		.show();
		this.finish();
	}

	private void setRunTypeInformation(Run r) {
		switch(this.runType) {
		case 0:
			EditText mtrForLap = (EditText) findViewById(R.id.mtrForLap);
			r.type.distance = mtrForLap.getText().toString();
			break;
			
		case 1:
			EditText mnsForLap = (EditText) findViewById(R.id.mnsForLap);
			r.type.time = mnsForLap.getText().toString();
			break;			
		}		
	}

	@Override
	public void callback(String res) {

		try {
			this.allRunners = new JSONArray(res);

			String[] runners = new String[allRunners.length()];
			for (int i = 0; i < allRunners.length(); i++) {
				JSONObject obj = new JSONObject(allRunners.get(i).toString());
				runners[i] = (String) obj.get("username");
			}

			this.runnersAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_multiple_choice, runners);
			this.runnersListView = (ListView) findViewById(R.id.runnersList);
			this.runnersListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			this.runnersListView.setAdapter(this.runnersAdapter);

		} catch (JSONException e) {
			Log.e("LOLCAT", "callback() : " + e.getMessage());
		}

	}

}
