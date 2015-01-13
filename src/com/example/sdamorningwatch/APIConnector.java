package com.example.sdamorningwatch;

import android.annotation.SuppressLint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;

@SuppressLint("DefaultLocale")
public class APIConnector {

	private static String url = "http://niritech.co/morning_watch_php/index.php";
	
	private ArrayList<MorningWatchBO> weeklyVersesList;
	static final String[] Days = new String[] { "Sunday", "Monday", "Tuesday",
		"Wednesday", "Thursday", "Friday", "Saturday"};
	
	@SuppressLint("DefaultLocale")
	public void getJSONObject(){
		
		HttpClient client = new DefaultHttpClient();
		
		ArrayList<MorningWatchBO> listMorningWatchBO = new ArrayList<MorningWatchBO>();
		//JSONObject jObject = new JSONObject();
		String result = null;
		url = url + "?param=app";
		//System.err.println("This is response from Server first" + url.toString());
		HttpPost post = new HttpPost(url);
		try {
			try {
				post.setEntity(new StringEntity("param=app"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			post.setHeader("Accept", "application/json");
	        post.setHeader("Content-type", "application/json");
			post.setURI(new URI(url));
			try {
					HttpResponse response = client.execute(post);
					StatusLine status = response.getStatusLine();
					int statusCode = status.getStatusCode();
					
					//On success connection to server
					if(statusCode == 200){
						BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
						StringBuffer sb = new StringBuffer("");
				        String line="";
			            
				        while ((line = in.readLine()) != null) {
			              sb.append(line);
			              break;
			            }
			           
			            result = sb.toString();
				         try {
				        	   JSONObject jObject=new JSONObject(result);
				        	   String data = jObject.getString("verses");
				        	   System.err.println("JOBJECT Length " + jObject.length());
				        	   for(Integer x = 0;x < jObject.length();++x){
				        		   
				        		   
				        		   
				        		   jObject = new JSONObject(data);
				        		   if(jObject.length() == x){
				        			   break;
				        		   }
				        		   System.err.println("JOBJECT Length2 " + jObject.length() + " X is " + x);
				        		   String str =  jObject.getString(x.toString());   
				        		   
				        		   System.err.println("String from Object @ " + x +":"+str);
				        		   JSONArray jString = new JSONArray("["+str+"]");
				        		   System.err.println("SIZE of JARRAY"+jString.length() + " Size of jObect new " + jObject.length() + " old X as "+x);
				        		   jObject = jString.getJSONObject(0);
				        		   
				        		    MorningWatchBO morningWatchBO = new MorningWatchBO();
				        		    morningWatchBO.setDays( Days[x].toUpperCase());
									morningWatchBO.setLink(jObject.getString("Url").toString());
									morningWatchBO.setTitle(jObject.getString("BibleVerse").toString() + " - " + jObject.getString("Entry_Date").toString());
									morningWatchBO.setDescription(jObject.getString("Description").toString());
									morningWatchBO.setSubject(jObject.getString("BibleVerse").toString());
									morningWatchBO.setDate(jObject.getString("Entry_Date").toString());
									listMorningWatchBO.add(morningWatchBO);
				        		   
				        		   System.out.println("Description from JSON " + jObject.getString("Description").toString());
				        		  
									
				        	   	}
							setWeeklyVersesList(listMorningWatchBO);
				         	}catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
				         	}   
				            in.close();
					//Connection status If Block Closes here
					}else{
						// TODO Add what happens when connection fails.
						System.err.println("Failed to connect to server");
					}
					
			     //Outter Try block closes here 
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			//Extreme outter try closes here 
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public ArrayList<MorningWatchBO> getWeeklyVersesList() {
		return weeklyVersesList;
	}

	public void setWeeklyVersesList(ArrayList<MorningWatchBO> weeklyVersesList) {
		this.weeklyVersesList = weeklyVersesList;
	}
	
	
	
}
