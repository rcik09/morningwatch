package com.example.sdamorningwatch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.niritech.doa.SQLLiteController;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TodayActivity extends Activity {

	private MorningWatchReader reader;
	static final String[] Days = new String[] { "Sunday", "Monday", "Tuesday",
		"Wednesday", "Thursday", "Friday", "Saturday"};
	static Button button;
	static int today;
	//static TextView favText;
	public static String todayDay;
	//Bottom navigation buttons
	static Button weeklyVersesBtn;
	static Button favBtn;
	static CheckBox favCheck;
	static Button settingBtn;
	static Button exitBtn;
	
	//Database
	private SQLLiteController liteDb;
	
	//static Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_today);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
		
		Calendar calendar = Calendar.getInstance();
		today = calendar.get(Calendar.DAY_OF_WEEK);
		
		/*ActionBar abar = getActionBar();
		abar.hide();
		abar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#74bc00")));*/
		//TextView toDay = (TextView)findViewById(R.id.textView2);
		TextView title = (TextView)findViewById(R.id.mainTitle_singl);
		TextView description = (TextView)findViewById(R.id.description_single);
		//TextView link = (TextView)findViewById(R.id.link_single);
		
		
		TextView topTitle = (TextView)findViewById(R.id.todayActivity);
		TextView versesRead = (TextView)findViewById(R.id.verses_read);
		TextView dayNum = (TextView)findViewById(R.id.menuTitle);
		TextView weekDay = (TextView)findViewById(R.id.textView2);
		TextView weekDate = (TextView)findViewById(R.id.textView3);
		TextView shareText = (TextView)findViewById(R.id.share);
		//favText = (TextView)findViewById(R.id.fav);
		favCheck = (CheckBox)findViewById(R.id.fav);
		TextView tipTitle = (TextView)findViewById(R.id.tipTitle);
		TextView bibleTip = (TextView)findViewById(R.id.bibleTip);
		
		//Add Fonts
		Typeface fonts = Typeface.createFromAsset(getAssets(), "fonts/DancingScript-Regular.ttf");
		Typeface robotoFonts = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
		//txV.setTypeface(fonts);
		topTitle.setTypeface(robotoFonts);
		versesRead.setTypeface(robotoFonts);
		dayNum.setTypeface(robotoFonts);
		dayNum.setText("Day "+today);
		weekDay.setTypeface(robotoFonts);
		weekDate.setTypeface(robotoFonts);
		shareText.setTypeface(robotoFonts,Typeface.BOLD);
		
		favCheck.setTypeface(robotoFonts,Typeface.BOLD);
		tipTitle.setTypeface(robotoFonts);
		bibleTip.setTypeface(robotoFonts);
		
		//ProgressBar
		final ProgressBar pbar = (ProgressBar)findViewById(R.id.progressBar1);
		
		
		shareText.setText("SHARE");
		shareText.setTextColor(Color.parseColor("#747474"));
		liteDb = new SQLLiteController(getApplicationContext());
		
		if(SplashScreen.getListOfVerses() != null && SplashScreen.getListOfVerses().size() > 0){
			//liteDb.deleteAllVerses();
			liteDb.addWeeklyVerses(SplashScreen.getListOfVerses());
			System.err.println("Added verses to DB");
		}
		
		if(liteDb.getTodayVerse().getFavourite() != null){
			if(liteDb.getTodayVerse().getFavourite().equals("Y")){
				favCheck.setText("ADD TO FAVOURITE");
				
				favCheck.setChecked(true);
				favCheck.setTextColor(Color.parseColor("#ffa423"));
			}else{
				favCheck.setText("ADD TO FAVOURITE");
				favCheck.setTextColor(Color.parseColor("#747474"));
			}
		}
		tipTitle.setText("Bible Study Tip");
		tipTitle.setTextColor(Color.parseColor("#ffa423"));
		bibleTip.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi risus magna, dictum at eleifend eu, consequat nec urna. Sed varius maximus odio nec tempus. Praesent accumsan massa ac orci consectetur, vel scelerisque velit fermentum. ileo facilisis quam dapibus finibus.");
		
		
		
		
		
		//System.err.println("Listing is NULL " + (SplashScreen.getListOfVerses() == null) + " What about the DB " + (liteDb.getAllVersesForWeek() == null) + "verses DB COunt " + liteDb.getVersesCount());
		
		if(liteDb.getAllVersesForWeek() != null && liteDb.getAllVersesForWeek().size()>0){
			int x = 0;
			for(MorningWatchBO verse : liteDb.getAllVersesForWeek()){
			//MorningWatchBO verse = new MorningWatchBO();
			//verse = liteDb.getTodayVerse();
				
				String day = Days[x];
				//System.err.println("DAY " + Days[x] + " Title " + verse.getTitle() + " Is it Today " + isItToday(day));
				if(isItToday(day)){
					System.out.println("Verse for : " + day);
					setTodayDay(day);
					weekDay.setText(day);
					title.setText(verse.getTitle());
					title.setTextColor(Color.parseColor("#646464"));
					title.setTypeface(robotoFonts, Typeface.BOLD);
					description.setText(verse.getDescription());
					description.setTypeface(robotoFonts);
					description.setTextColor(Color.parseColor("#646464"));
					weekDate.setText(verse.getDate());
					//link.setText(verse.getLink());
					
					
				}
				
				x += 1;
			}
			
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					
					//Add the incremental value from database as parameter of the 
					pbar.setProgress((int)calculatePercentage(today));
				}
				
			}, 1000);
			
			
        }
		
	/*	 button = (Button) findViewById(R.id.button1);*/
        favCheck.setOnClickListener(new OnClickListener() {
		
       
        	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(favCheck.isChecked()){
					SQLLiteController liteDbn = new SQLLiteController(getApplicationContext());
					liteDbn.addFavouriteVerse(liteDbn.getTodayVerse(),"Y");
					favCheck.setTextColor(Color.parseColor("#ffa423"));
					liteDbn.close();
				}else{
					SQLLiteController liteDbn = new SQLLiteController(getApplicationContext());
					liteDbn.addFavouriteVerse(liteDbn.getTodayVerse(),"N");
					favCheck.setTextColor(Color.parseColor("#747474"));
					liteDbn.close();
				}
				
				
			}
		});
        
       shareText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent sendIntent = new Intent();
				StringBuilder sb = new StringBuilder();
				if(SplashScreen.getListOfVerses() != null && SplashScreen.getListOfVerses().size() > 0){
		        	
					for(MorningWatchBO verse : SplashScreen.getListOfVerses()){
						String day = verse.getDays();
						if(isItToday(day)){
							//System.out.println("Verse for : " + day);
							//sb.append(Html.fromHtml("<p style='color:blue;font-size:14px;'>"+verse.getDays()+"</p><br/>"));
							sb.append(Html.fromHtml("<p style='color:blue;font-size:10px;'>"+verse.getTitle()+"</p><br/>"));
							sb.append(Html.fromHtml("<p style='color:blue;font-size:12px;'>"+verse.getDescription()+"</p><br/>"));
							sb.append(Html.fromHtml("<a style='color:blue;font-size:10px;' href='"+verse.getLink()+"'>"+verse.getLink()+"</a><br/>"));
							sb.append(Html.fromHtml("<a style='color:blue;font-size:10px;' href='www.google.com'>From Android Morning Watch</a><br/>"));
							//title.setText(verse.getTitle());
							//description.setText(verse.getDescription());
							//link.setText(verse.getLink());
						}
					}
		        }
				
				sendIntent.setType("text/plain");
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_SUBJECT, "New firends");
				sendIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());
				
				startActivity(Intent.createChooser(sendIntent, "Morning Watch Share"));
				System.err.println("About to start Activity MAIN");
				
				
			}
		});
       
       //Navigation Buttons click events
       
       weeklyVersesBtn = (Button)findViewById(R.id.weeklyBtn);
       favBtn =(Button)findViewById(R.id.favouritesBtn);
       settingBtn = (Button)findViewById(R.id.aboutBtn);
       exitBtn = (Button)findViewById(R.id.exitBtn);
       
       		weeklyVersesBtn.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if(event.ACTION_DOWN != 0){
						weeklyVersesBtn.setBackgroundColor(Color.parseColor("#1bc1ff"));
						return true;
					}
					return false;
				}
			});
	       weeklyVersesBtn.setOnClickListener(new OnClickListener() {
			
				@Override
				public void onClick(View v) {
					Intent ntent = new Intent(TodayActivity.this,MainActivity.class);
					ntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(ntent);
					//finish();
				}
			});
	       
	       favBtn.setOnClickListener(new OnClickListener() {
			
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
	       
	      settingBtn.setOnClickListener(new OnClickListener() {
			
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
	      });
	      
	     exitBtn.setOnClickListener(new OnClickListener() {
			
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
			});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.today, menu);
		
		return true;
	}
	
	public double calculatePercentage(int input){
		int val = 100;
		final int daysNum = 7;
		double perc = 0.0;
		System.err.println("input "+input +" cal " + input/(double)daysNum);
		perc = (input/(double)daysNum)*val;
		System.err.println("Percentage "+perc+"%");
		return perc;
	}
	
	public Boolean isItToday(String day){
		
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		int today = calendar.get(Calendar.DAY_OF_WEEK);
		System.err.println("Today is index " + today);
		int dayIndex = 999;
		for (int i = 0; i < Days.length; i++) {
			if(day.equals(Days[i])){
				dayIndex = i;
				break;
			}
		}
		
		System.err.println("I entered index " +day +":" + dayIndex);
		if(today-1 == dayIndex){
			return true;
		}
		
		return false;
	}

	public static String getTodayDay() {
		return todayDay;
	}

	public static void setTodayDay(String todayDay) {
		TodayActivity.todayDay = todayDay;
	}
	
	
}
