package com.niritech.doa;



import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.sdamorningwatch.MorningWatchBO;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class MySQLDBController {

	private final static String url = "jdbc:mysql://<address>/<database>";
	private final static String user = "username";
	private final static String password="password";
	
	//This class should not be synchronized
	public MySQLDBController(){
		
		
		
	}
	
	public static void getConnection(){
		try {
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Connection con = (Connection)DriverManager.getConnection(url, user, password);
			MorningWatchBO morningWatchBO = new MorningWatchBO();
			if(con != null){
				System.err.println("Database connection Established successfully !!!!!!!!!!!!!");
				
				Statement statement = (Statement) con.createStatement();
				
				ResultSet result =  statement.executeQuery("Select * from appdata where Seen = 'N'");
				
				while(result.next()){
					System.err.println("Description " + result.getInt("Description"));
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
