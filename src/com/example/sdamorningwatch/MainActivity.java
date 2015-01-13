package com.example.sdamorningwatch;





import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.niritech.doa.SQLLiteController;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	String[] menuOptions = new String[] { "Home", "Weekly Verses", "Favourites","About Morning Watch","Exit" };
	//Database
	private SQLLiteController liteDb;
	
	static CheckBox favCheckBx;
	//TextView shareTextBtn;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        ActionBar abar = getActionBar();
        getActionBar().setIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		abar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#74bc00")));
        
        ListView menuListView = null;
        menuListView = (ListView)findViewById(R.id.menuID);
        liteDb = new SQLLiteController(getApplicationContext());
        
        Typeface robotoFonts = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
       
		
        if(SplashScreen.getListOfVerses() != null && SplashScreen.getListOfVerses().size() > 0){
        	ListView lv;
        	
        	
	         lv = (ListView)findViewById(R.id.listView2);
	         lv.setAdapter(new CustomAdapter((ArrayList<MorningWatchBO>)liteDb.getAllVersesForWeek(), getApplicationContext()));
	         menuListView.setAdapter(new MenuListAdapter(getApplicationContext(), menuOptions));
	         liteDb.close();
	         //(ArrayList<MorningWatchBO>)liteDb.getAllVersesForWeek() SplashScreen.getListOfVerses()
        }
        menuListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				System.err.println("Position " + position);
				if(menuOptions[position].contains("Home")){
					Intent i = new Intent(MainActivity.this,TodayActivity.class);
					startActivity(i);
					//finish();
					
				}
				if(menuOptions[position].contains("Weekly Verses")){
					/*Intent i = new Intent(MainActivity.this,MainActivity.class);
					startActivity(i);*/
					
				}
				if(menuOptions[position].contains("Favourites")){
					/*Intent i = new Intent(MainActivity.this,TodayActivity.class);
					startActivity(i);*/
					
				}
				if(menuOptions[position].contains("About Morning Watch")){
					/*Intent i = new Intent(MainActivity.this,TodayActivity.class);
					startActivity(i);*/
					
				}
				if(menuOptions[position].contains("Exit")){
					/*Intent i = new Intent(MainActivity.this,TodayActivity.class);
					startActivity(i);*/
					System.exit(0);
				}
			}
		
        });
        
    }
        
  
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }
    
    
    
   



	
    
}


