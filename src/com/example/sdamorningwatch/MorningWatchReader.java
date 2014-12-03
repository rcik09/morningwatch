package com.example.sdamorningwatch;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MorningWatchReader extends ListActivity{

	public final static String morningWatchUrl =  "http://www.morningwatch.org/rss.xml";
	private ListView lv;
	static final String[] Days = new String[] { "Sunday", "Monday", "Tuesday",
		"Wednesday", "Thursday", "Friday", "Saturday"};
	
	private String[] titles = new String[5];
	
	private View rowView;
	
	public ArrayList<MorningWatchBO> readXml()
	{
		ArrayList<MorningWatchBO> listMorningWatchBO = new ArrayList<MorningWatchBO>();
		List<String> testList = new ArrayList<String>();
		
		 try {
			 
			  	 URL url = new URL(morningWatchUrl);
			     HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			     if(connection.getResponseCode() == HttpURLConnection.HTTP_OK)
			     {
			    	 System.out.println("Connection Establiish");
			     }else
			    	 System.err.println("No connection");
			     
			    	 InputStream in = new BufferedInputStream(connection.getInputStream());

			    System.out.println(connection.getAllowUserInteraction());
			     Document doc = parseXML(in);
			     
				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			 
				NodeList nList = doc.getElementsByTagName("item");
			 
				System.out.println("----------------------------");
				//LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				
				//rowView = inflater.inflate(R.layout.morning_watch,null);
				/*TextView titleTV = (TextView)findViewById(R.id.title);
				TextView linkTV = (TextView)findViewById(R.id.link);
				TextView DescTV = (TextView)findViewById(R.id.Desc);*/
				
				for (int temp = 0; temp < nList.getLength(); temp++) {
					//System.out.println("number of nodes " + nList.getLength());
					Node nNode = nList.item(temp);
					//System.out.println("nList.item " + nList.item(temp));
					System.out.println("\nCurrent Element :" + nNode.getNodeName());
			 
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						MorningWatchBO morningWatchBO = new MorningWatchBO();
						Element eElement = (Element) nNode;
						
						/*titleTV.setText(eElement.getElementsByTagName("title").item(0).getTextContent());
						linkTV.setText(eElement.getAttribute("rdf:about"));
						DescTV.setText(eElement.getElementsByTagName("description").item(0).getTextContent());*/
						morningWatchBO.setDays(Days[temp].toUpperCase().toString());
						morningWatchBO.setLink(eElement.getAttribute("rdf:about"));
						morningWatchBO.setTitle(eElement.getElementsByTagName("title").item(0).getTextContent());
						morningWatchBO.setDescription(eElement.getElementsByTagName("description").item(0).getTextContent());
						morningWatchBO.setSubject(eElement.getElementsByTagName("dc:subject").item(0).getTextContent());
						
						//titles[temp] = eElement.getElementsByTagName("title").item(0).getTextContent();
						//testList.add(eElement.getElementsByTagName("title").item(0).getTextContent());
						listMorningWatchBO.add(morningWatchBO);
						
						/*System.out.println("Link : " + listMorningWatchBO.get(temp).getLink());
						System.out.println("Title : " + listMorningWatchBO.get(temp).getTitle());
						System.out.println("Link : " + listMorningWatchBO.get(temp).getLink());
						System.out.println("Description : " + listMorningWatchBO.get(temp).getDescription());
						System.out.println("Subject : " + listMorningWatchBO.get(temp).getSubject());*/
			 
					}
				}
				
				/*lv = (ListView)findViewById(R.id.listView1);*/
				// Binding data
		       /* ArrayAdapter<MorningWatchBO> adapter = new ArrayAdapter<MorningWatchBO>(this,R.layout.layout_template, listMorningWatchBO);
		        lv.setAdapter(adapter);*/
				
				/*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.layout_template, FRUITS);
		        lv.setAdapter(adapter);*/
		        
		 		}catch (Exception e) 
		 			{
		 			e.printStackTrace();
			    }
		 		return listMorningWatchBO;
		 
	}
	
	private Document parseXML(InputStream stream)
	{
				System.out.println("Inside parse");
		        DocumentBuilderFactory objDocumentBuilderFactory = null;
		        DocumentBuilder objDocumentBuilder = null;
		        Document doc = null;
		        try
		        {
		            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
		            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

		            doc = objDocumentBuilder.parse(stream);
		            System.out.println("Doc returned");
		        }
		        catch(Exception ex)
		        {
		        	System.out.println("Parse Except");
		        	ex.printStackTrace();
		            
		        }       

		        return doc;
		    }

}
	

