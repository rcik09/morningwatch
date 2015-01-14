package com.example.sdamorningwatch;

import java.util.Calendar;
import java.util.Locale;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DailyVerse extends FragmentActivity {
	
	static final String[] Days = new String[] { "Sunday", "Monday", "Tuesday",
		"Wednesday", "Thursday", "Friday", "Saturday"};

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_daily_verse);
		ActionBar ab = getActionBar();
		//ab.hide();
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.daily_verse, menu);
		return true;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 7 total pages.
			return 7;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			
			switch (position) {
			case 0:
				
				
				if(SplashScreen.getListOfVerses() != null && SplashScreen.getListOfVerses().size() > 0){
		        	
					return SplashScreen.getListOfVerses().get(0).getDays().toUpperCase(l);
					
		        }
				
			case 1:
				
				if(SplashScreen.getListOfVerses() != null && SplashScreen.getListOfVerses().size() > 0){
		        	
						return SplashScreen.getListOfVerses().get(1).getDays().toUpperCase(l);
					
		        }
			case 2:
				if(SplashScreen.getListOfVerses() != null && SplashScreen.getListOfVerses().size() > 0){
					return SplashScreen.getListOfVerses().get(2).getDays().toUpperCase(l);
				}
			case 3:
				if(SplashScreen.getListOfVerses() != null && SplashScreen.getListOfVerses().size() > 0){
		        	
					return SplashScreen.getListOfVerses().get(3).getDays().toUpperCase(l);
					
		        }
			case 4:
				if(SplashScreen.getListOfVerses() != null && SplashScreen.getListOfVerses().size() > 0){
		        	
					return SplashScreen.getListOfVerses().get(4).getDays().toUpperCase(l);
					
		        }
			case 5:
				if(SplashScreen.getListOfVerses() != null && SplashScreen.getListOfVerses().size() > 0){
		        	
					return SplashScreen.getListOfVerses().get(5).getDays().toUpperCase(l);
					
		        }
			case 6:
				if(SplashScreen.getListOfVerses() != null && SplashScreen.getListOfVerses().size() > 0){
		        	
					return SplashScreen.getListOfVerses().get(6).getDays().toUpperCase(l);
					
		        }
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_daily_verse_dummy, container, false);
			
			//TextView toDay = (TextView)rootView.findViewById(R.id.Days_single);
			TextView title = (TextView)rootView.findViewById(R.id.mainTitle_singl);
			TextView description = (TextView)rootView.findViewById(R.id.description_single);
			TextView link = (TextView)rootView.findViewById(R.id.link_single);
			
			if(SplashScreen.getListOfVerses() != null && SplashScreen.getListOfVerses().size() > 0){
	        	
				//for(MorningWatchBO verse : SplashScreen.getListOfVerses()){
					//String day = verse.getDays();
					//if(isItToday(day)){
						//System.out.println("Verse for : " + day);
						int index = getArguments().getInt(ARG_SECTION_NUMBER) - 1;
						
						//toDay.setText(SplashScreen.getListOfVerses().get(index).getDays());
						title.setText(SplashScreen.getListOfVerses().get(index).getTitle());
						description.setText(SplashScreen.getListOfVerses().get(index).getDescription());
						link.setText(SplashScreen.getListOfVerses().get(index).getLink());
				}
				//}
	       // }
			
			/*TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));*/
			return rootView;
		}
		
		
		public Boolean isItToday(String day){
			Locale l = Locale.getDefault();
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			int today = calendar.get(Calendar.DAY_OF_WEEK);
			System.err.println("Today is index " + today);
			int dayIndex = 999;
			for (int i = 0; i < Days.length; i++) {
				if(day.equals(Days[i].toUpperCase())){
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
	}

}
