package com.example.sdamorningwatch;



import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DBController {

	private final static String url = "jdbc:mysql://23.229.188.91:3306/morningWatch";
	private final static String user = "watchuser";
	private final static String password="whatpassword";
	
	//This class should not be synchronized
	public DBController(){
		
		
		
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
