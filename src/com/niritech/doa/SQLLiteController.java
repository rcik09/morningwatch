package com.niritech.doa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.example.sdamorningwatch.APIConnector;
import com.example.sdamorningwatch.MorningWatchBO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Message;
import android.text.format.DateFormat;

public class SQLLiteController extends SQLiteOpenHelper{

	static final String  databaseName = "newMorningWatchDB";
	static final String tableName = "morningwatch";
	static final int version = 1;
	private Context context;
	SQLiteDatabase db;
	
	public SQLLiteController(Context context) {
		super(context, databaseName, null, version);
		this.context = context;
		 db = this.getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String query = "CREATE TABLE "+tableName+" ("+
						  "ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
						  "BibleVerse TEXT," +
						  "Description TEXT," +
						  "Url TEXT," +
						  "Entry_Date TEXT," +
						  "Favourites TEXT)";
		System.err.println("SQL " + query);
		db.execSQL(query);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS"+tableName);
		onCreate(db);
		//db.close();
		
	}
	
	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */
	
	public void deleteAllVerses() {
		
		SQLiteDatabase nDb = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		nDb.delete(tableName, "1 = 1", null);
	}

	// Adding verses
	public void addWeeklyVerses(List<MorningWatchBO> verses) {
		

		ContentValues values = new ContentValues();
		 SQLiteDatabase nDb = this.getWritableDatabase();
		 System.err.println("THIS IS add weekly size" + verses.size());
		 for(int x = 0;x < verses.size();x++){
		//for(MorningWatchBO verse:verses){
			values.put("BibleVerse", verses.get(x).getTitle());
			values.put("Description", verses.get(x).getDescription());
			values.put("Url", verses.get(x).getLink());
			values.put("Entry_Date", verses.get(x).getDate());
			values.put("Favourites", "N");
			System.err.println(" Does this record exists "+verses.get(x).getDate()+ doesVerseExists(getTodayDate()));
			if(!doesVerseExists(verses.get(x).getDate())){
				// Inserting Records
				nDb.insert(tableName, null, values);
			}
			
		//}
		 }
		
			nDb.close(); // Closing database connection
		
	}
	
	//Check if record exists
	public Boolean doesVerseExists(String todayDate){
		String query = "SELECT  * FROM "+tableName+" where Entry_Date = '"+todayDate+"'";
		
		 SQLiteDatabase nDb = this.getWritableDatabase();
		Cursor cursor = nDb.rawQuery(query, null);
		if(cursor.getCount() > 0){
			return true;
		}
		return false;
	}
	

	// Getting all verses
	public List<MorningWatchBO> getAllVersesForWeek() {
		List<MorningWatchBO> verses = new ArrayList<MorningWatchBO>();
		SQLiteDatabase db = this.getReadableDatabase();
		//Returns all verses for the week
		//String query = "Select * from " +tableName+" where Entry_Date >= (select strftime('%Y-%m-%d',datetime('now','-1 day' ))) ORDER BY Entry_Date ASC";
		Cursor  cursor = db.query(tableName, null, "Entry_Date >= (select strftime('%Y-%m-%d',datetime('now','-7 day' )))", null, null, null, "Entry_Date ASC");
		//Cursor  cursor = db.query(tableName, null, null, null, null, null, null);
		//Cursor  cursor = db.rawQuery(query,null);
		
		 // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	            MorningWatchBO morningWatchBO = new MorningWatchBO();
	            
	    		morningWatchBO.setTitle(cursor.getString(1));
	    		morningWatchBO.setDescription(cursor.getString(2));
	    		morningWatchBO.setLink(cursor.getString(3));
	    		morningWatchBO.setDate(cursor.getString(4));
	    		morningWatchBO.setFavourite(cursor.getString(5));
	            
	            // Adding verses to list
	            verses.add(morningWatchBO);
	        } while (cursor.moveToNext());
	    }
	    return verses;
	}
	
	// Getting all favourite verses
		public List<MorningWatchBO> getFavouriteVerses() {
			List<MorningWatchBO> verses = new ArrayList<MorningWatchBO>();
			SQLiteDatabase db = this.getReadableDatabase();
			String query = "SELECT  * FROM "+tableName+" where Favourites = 'Y'";
			Cursor  cursor = db.rawQuery(query,null);
			
			 // looping through all rows and adding to list
		    if (cursor.moveToFirst()) {
		        do {
		            MorningWatchBO morningWatchBO = new MorningWatchBO();
		            
		    		morningWatchBO.setTitle(cursor.getString(1));
		    		morningWatchBO.setDescription(cursor.getString(2));
		    		morningWatchBO.setLink(cursor.getString(3));
		    		morningWatchBO.setDate(cursor.getString(4));
		    		morningWatchBO.setFavourite(cursor.getString(5));
		            
		            // Adding verses to list
		            verses.add(morningWatchBO);
		        } while (cursor.moveToNext());
		    }
		    return verses;
		}
	
	
	// Gets Todays Verse
	public MorningWatchBO getTodayVerse() {
		SQLiteDatabase db = this.getReadableDatabase();
		//Gets the verse for today
		Cursor cursor = db.query(tableName, new String[] { "BibleVerse", "Description", "Url", "Entry_Date", "Favourites" }, "Entry_Date" + "='"+getTodayDate()+"'",null, null, null, null,
				null);
		
		MorningWatchBO morningWatchBO = new MorningWatchBO();
		
		if (cursor != null)
			cursor.moveToFirst();
		
		System.err.println("CURSOR SIZE " + cursor.getCount());
		
		if(cursor.getCount() > 0){
		
			morningWatchBO.setTitle(cursor.getString(0));
			morningWatchBO.setDescription(cursor.getString(1));
			morningWatchBO.setLink(cursor.getString(2));
			morningWatchBO.setDate(cursor.getString(3));
			morningWatchBO.setFavourite(cursor.getString(4));
		}
		return morningWatchBO;
	}
	
	// Add Favourites
		public void addFavouriteVerse(MorningWatchBO verse, String param) {
			

			ContentValues values = new ContentValues();
			
			values.put("BibleVerse", verse.getTitle());
			values.put("Description", verse.getDescription());
			values.put("Url", verse.getLink());
			values.put("Entry_Date", verse.getDate());
			values.put("Favourites", param);
			
			if(doesVerseExists(getTodayDate())){
				// Inserting Records
				db.updateWithOnConflict(tableName, values, " Entry_Date" + " = '"+verse.getDate()+"'" ,null,SQLiteDatabase.CONFLICT_REPLACE);
			}else{
				db.insert(tableName, null, values);
			}
			/*
			if(doesVerseExists(getTodayDate())){
				db.delete(tableName, "Entry_Date = '"+verse.getDate()+"'", null);
				db.insert(tableName, null, values);
			}*/
			
			db.close(); // Closing database connection
		}

	// Getting All Printers
	/*public List<Printer> getAllPrinters() {
		List<Printer> printerList = new ArrayList<Printer>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_PrinterAddressTbl;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Printer printer = new Printer();
				printer.set_id(Integer.parseInt(cursor.getString(0)));
				printer.set_printerName(cursor.getString(1));
				printer.set_ipAddress(cursor.getString(2));
				printer.set_port(cursor.getString(3));
				// Adding printer to list
				printerList.add(printer);
			} while (cursor.moveToNext());
		}

		return printerList;
	}

	// Updating single printer
	public int savePrinter(Printer printer) {
		SQLiteDatabase db = this.getWritableDatabase();
		int successVal = 0;

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, printer.get_printerName());
		values.put(KEY_IP, printer.get_ipAddress());
		values.put(KEY_PORT, printer.get_port());

		// Checks to see if printer exists in record
		if (getPrintersCount() < 1) {
			addPrinter(printer);
			System.out.println("Printer does not exists");
			successVal = 1;
		} else {

			successVal = db.update(TABLE_PrinterAddressTbl, values, KEY_ID + " = ?", new String[] { String.valueOf(1) });
			System.out.println("Printer exists and updated");
		}

		// updating row
		return successVal;
	}

	// Deleting single contact
	public void deletePrinter() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PrinterAddressTbl, KEY_ID + " = ?", new String[] { String.valueOf(1) });
		db.close();
	}*/

	// Getting contacts Count
	public int getVersesCount() {
		String countQuery = "SELECT  * FROM "+tableName;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		// cursor.close();

		// return count
		return cursor.getCount();
	}
	
	public String getTodayDate(){
		Date date = new Date();
		java.text.DateFormat example = new SimpleDateFormat("yyyy-MM-dd");
		example.setTimeZone(TimeZone.getTimeZone("GMT-5"));
		String todayDate = example.format(date).toString();
		
		return todayDate;
	}

}
