package com.example.sdamorningwatch;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuListAdapter extends ArrayAdapter<String>{

	private final String[] menuItems;
	private final Context context;
	
	public MenuListAdapter(Context context, String[] menuItems) {
		super(context,R.layout.menu_listview_template, menuItems);
		this.context = context;
		this.menuItems = menuItems;
	}
	
	@Override
	public View getView(int position,View convertView,ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.menu_listview_template, parent,false);
		TextView menuLabel = (TextView)rowView.findViewById(R.id.menuTitle);
		ImageView icon = (ImageView)rowView.findViewById(R.id.iconItem);
		menuLabel.setText(menuItems[position]);
		
		String s = menuItems[position];
		if(s.contains("Home")){
			icon.setImageResource(R.drawable.home);
		}
		if(s.contains("Weekly Verses")){
			menuLabel.setTextColor(Color.parseColor("#a9a9a9"));
			icon.setImageResource(R.drawable.verses);
		}
		if(s.contains("Favourites")){
			icon.setImageResource(R.drawable.fav);
		}
		if(s.contains("About Morning Watch")){
			icon.setImageResource(R.drawable.setting);
		}
		if(s.contains("Exit")){
			icon.setImageResource(R.drawable.exit);
		}
		
		return rowView;
	}

}
