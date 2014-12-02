package com.example.sdamorningwatch;





import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Handler handler;
	private MorningWatchReader reader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        AsyncTaskMan async = new AsyncTaskMan();
        async.execute();
    }
        
  
    
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private class AsyncTaskMan extends AsyncTask<String, String, ArrayList<MorningWatchBO>> {
	     
		
    	@Override
	     protected void onProgressUpdate(String... progress) {
	         
	     }
    	
    	@Override
	     protected void onPostExecute(ArrayList<MorningWatchBO> result) {
	         ListView lv;
	         lv = (ListView)findViewById(R.id.listView1);
	         //ArrayAdapter<MorningWatchBO> adapt = new ArrayAdapter<MorningWatchBO>(getApplicationContext(), R.layout.layout_template,result);
	         //lv.setAdapter(adapt);
	         lv.setAdapter(new CustomAdapter(result, getApplicationContext()));
	     }

		@Override
		protected ArrayList<MorningWatchBO> doInBackground(String... arg0) {
			Looper.prepare();
			 reader = new MorningWatchReader();
			  
			//Looper.loop();  
			  return (ArrayList<MorningWatchBO>) reader.readXml();
			  
		}
    }
    
}


