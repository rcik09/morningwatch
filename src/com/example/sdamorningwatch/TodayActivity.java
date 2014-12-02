package com.example.sdamorningwatch;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TodayActivity extends Activity {

	private MorningWatchReader reader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_today);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.today, menu);
		return true;
	}

}
