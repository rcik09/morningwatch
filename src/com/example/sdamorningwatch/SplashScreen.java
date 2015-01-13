package com.example.sdamorningwatch;

import java.util.ArrayList;

import com.niritech.doa.MySQLDBController;
import com.niritech.doa.SQLLiteController;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

public class SplashScreen extends Activity {

	private static int Splash_time_out = 1000;
	private Handler handler;
	private MorningWatchReader reader;
	private MySQLDBController db;
	private SQLLiteController liteDb;
	private APIConnector api;
	private static ArrayList<MorningWatchBO> listOfVerses;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
		
		liteDb = new SQLLiteController(getApplicationContext());
		SQLiteDatabase sqlite = liteDb.getWritableDatabase();
		//liteDb.addVerses((ArrayList<MorningWatchBO>) nApi.getWeeklyVersesList());
		sqlite.close();
		AsyncTaskMan async = new AsyncTaskMan();
        async.execute();
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spalsh_screen, menu);
		return true;
	}
	
	 public static ArrayList<MorningWatchBO> getListOfVerses() {
			return listOfVerses;
		}


	public static void setListOfVerses(ArrayList<MorningWatchBO> listOfVerses) {
			SplashScreen.listOfVerses = listOfVerses;
		}
	
	private class AsyncTaskMan extends AsyncTask<String, String, ArrayList<MorningWatchBO>> {
	     
	@Override
	     protected void onProgressUpdate(String... progress) {
	         
	     }
 	
 	@Override
	     protected void onPostExecute(ArrayList<MorningWatchBO> result) {
	 		SplashScreen.setListOfVerses(result); 
	 		
	 		new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					
					Intent i = new Intent(SplashScreen.this,TodayActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(i);
					finish();
				}
				
			}, Splash_time_out);
	     }

		@Override
		protected ArrayList<MorningWatchBO> doInBackground(String... arg0) {
			Looper.prepare();
			
			APIConnector nApi = new APIConnector();
			nApi.getJSONObject();  
			
			return (ArrayList<MorningWatchBO>) nApi.getWeeklyVersesList();
			  
		}
	}

}
