package com.example.sdamorningwatch;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

	    private ArrayList<MorningWatchBO> _data;
	    Context _c;
	    
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
	           TextView link = (TextView)v.findViewById(R.id.link);

	           MorningWatchBO morningWatch = _data.get(position);
	          // image.setImageResource(msg.icon);
	           days.setText(morningWatch.getDays());
	           mainTitle.setText(morningWatch.getTitle());
	          // title.setText("Title: "+morningWatch.getTitle());
	           desc.setText(morningWatch.getDescription());
	           link.setText(morningWatch.getLink());		              		
	        
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
	        
	        return v; 
	}
	
	
}
