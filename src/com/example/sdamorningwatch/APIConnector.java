package com.example.sdamorningwatch;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class APIConnector {

	private final static String url = "http://niritech.co/morning_watch_php/api.php";
	public void getJSONObject(){
		
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		
	}
	
}
