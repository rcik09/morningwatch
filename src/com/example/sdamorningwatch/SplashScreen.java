package com.example.sdamorningwatch;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class SplashScreen extends Activity {

	private static int Splash_time_out = 10000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		new Handler().postDelayed(new Runnable() {
					
					
					
					@Override
					public void run() {
						
						
						
						Intent i = new Intent(SplashScreen.this,MainActivity.class);
						startActivity(i);
						
						finish();
						
						overridePendingTransition(R.anim.fadein,R.anim.fadeout);
					}
					
				}, Splash_time_out);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spalsh_screen, menu);
		return true;
	}

}
