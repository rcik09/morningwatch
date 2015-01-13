package com.example.sdamorningwatch;

import java.util.ArrayList;

import com.niritech.doa.SQLLiteController;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

	    private ArrayList<MorningWatchBO> _data;
	    Context _c;
	    
	    static final String[] Days = new String[] { "Sunday", "Monday", "Tuesday",
			"Wednesday", "Thursday", "Friday", "Saturday"};
	    
	  //Database
		private SQLLiteController liteDb;
		
		static CheckBox favCheckBx;
		TextView shareTextBtn;
		
		MorningWatchBO morningWatch;
	    
	    CustomAdapter (ArrayList<MorningWatchBO> data, Context c){
	        _data = data;
	        _c = c;
	    }
	   
	    public int getCount() {
	        // TODO Auto-generated method stub
	        return _data.size();
	    }
	    
	    public Object getItem(int position) {
	        // TODO Auto-generated method stub
	        return _data.get(position);
	    }

	    public long getItemId(int position) {
	        // TODO Auto-generated method stub
	        return position;
	    }
	   
	    public View getView(final int position, View convertView, ViewGroup parent) {
	        // TODO Auto-generated method stub
	         View v = convertView;
	         
	        morningWatch = _data.get(position);
	         
	         if (v == null) 
	         {
	            LayoutInflater vi = (LayoutInflater)_c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            v = vi.inflate(R.layout.morning_watch, null);
	         }

	           //ImageView image = (ImageView) v.findViewById(R.id.icon);
	           TextView days = (TextView)v.findViewById(R.id.Days);
	           TextView mainTitle = (TextView)v.findViewById(R.id.mainTitle);
	           //TextView title = (TextView)v.findViewById(R.id.title);
	           TextView desc = (TextView)v.findViewById(R.id.description);
	          // TextView link = (TextView)v.findViewById(R.id.link);
	           
	           liteDb = new SQLLiteController(v.getContext().getApplicationContext());
	           
	           Typeface robotoFonts = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/Roboto-Regular.ttf");
	           
	           shareTextBtn = (TextView)v.findViewById(R.id.newlistshare);
	           //System.err.println("share texr "+shareTextBtn.getText());
	           
	          // shareTextBtn.setTypeface(robotoFonts);
	           shareTextBtn.setText("SHARE");
	   			shareTextBtn.setTextColor(Color.parseColor("#747474"));
	   			shareTextBtn.setTypeface(robotoFonts);
	   		 shareTextBtn.setOnClickListener(new OnClickListener() {
	   				
	   				@Override
	   				public void onClick(View v) {
	   					Context con = _c;
	   					Intent sendIntent = new Intent();
	   					StringBuilder sb = new StringBuilder();
	   					if(liteDb.getTodayVerse() != null){
	   			        	
	   								sb.append(Html.fromHtml("<p style='color:blue;font-size:10px;'>"+morningWatch.getTitle()+"</p><br/>"));
	   								sb.append(Html.fromHtml("<p style='color:blue;font-size:12px;'>"+morningWatch.getDescription()+"</p><br/>"));
	   								sb.append(Html.fromHtml("<a style='color:blue;font-size:10px;' href='"+morningWatch.getLink()+"'>"+morningWatch.getLink()+"</a><br/>"));
	   								sb.append(Html.fromHtml("<a style='color:blue;font-size:10px;' href='www.google.com'>From Android Morning Watch</a><br/>"));
	   						}
	   					
	   					sendIntent.setType("text/plain");
	   					sendIntent.setAction(Intent.ACTION_SEND);
	   					sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Morning Watch");
	   					sendIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());
	   					sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	   					_c.startActivity(Intent.createChooser(sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK), "Morning Watch Share"));
	   					System.err.println("About to start Activity MAIN");
	   					
	   					
	   				}
	   			});
	           
	   		favCheckBx = (CheckBox)v.findViewById(R.id.favInList);
	   		if(morningWatch.getFavourite() != null){
		   		if(morningWatch.getFavourite().equals("Y")){
					favCheckBx.setText("ADD TO FAVOURITE");
					
					favCheckBx.setChecked(true);
					favCheckBx.setTextColor(Color.parseColor("#ffa423"));
				}else{
					favCheckBx.setTextColor(Color.parseColor("#747474"));
				}
	   		}
	   		favCheckBx.setTypeface(robotoFonts);
	        
	   	 favCheckBx.setOnClickListener(new OnClickListener() {
	 		
	         
	        	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if(favCheckBx.isChecked()){
						SQLLiteController liteDbn = new SQLLiteController(v.getContext().getApplicationContext());
						liteDbn.addFavouriteVerse(morningWatch,"Y");
						favCheckBx.setTextColor(Color.parseColor("#ffa423"));
						liteDbn.close();
					}else{
						SQLLiteController liteDbn = new SQLLiteController(v.getContext().getApplicationContext());
						liteDbn.addFavouriteVerse(morningWatch,"N");
						favCheckBx.setTextColor(Color.parseColor("#747474"));
						liteDbn.close();
					}
					
					
				}
			});
	          
	          // image.setImageResource(msg.icon);
	           days.setText(Days[position]);
	           mainTitle.setText(morningWatch.getTitle());
	          // title.setText("Title: "+morningWatch.getTitle());
	           desc.setText(morningWatch.getDescription());
	         //  link.setText(morningWatch.getLink());		              		
	        
	/*           mainTitle.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder adb=new AlertDialog.Builder(Messages.this);
		        adb.setMessage("Add To Contacts?");
		        adb.setNegativeButton("Cancel", null);
		        final int selectedid = position;
		        final String itemname= (String) _data.get(position).getTitle();

		        adb.setPositiveButton("OK", new AlertDialog.OnClickListener() {
		        	public void onClick(DialogInterface dialog, int which) {
		        				
		        		System.out.println("Select " + selectedid);
		        		System.out.println("Project " + itemname);
		        		
		        		Bundle b = new Bundle();
		    			b.putString("project", itemname);
		    			Intent createTask = new Intent ("com.loginworks.tasktrek.CREATETASK");
		    			createTask.putExtras(b);
		    			startActivity(createTask);    	
		        		}});
		        
		        adb.show();      
			}    						
		});*/
	        liteDb.close();
	        return v; 
	}
	
	
}
