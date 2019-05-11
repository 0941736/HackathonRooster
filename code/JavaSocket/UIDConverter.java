package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UIDConverter {
	
	private Connection conn;
	
	public UIDConverter() {
		// Connect to the Database
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// Connect to the UID database
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DummyUIDs","gewad","gewad");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Connected to UID Database.");
	}
	
	public String getCode(String UID) {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT reserver FROM DymmyUIDs.uid WHERE uid.uid = '" + UID + "';");
			
			if(!result.next()) {
				return "";
			}
			return result.getString(1);
		} catch(SQLException e) {
			e.printStackTrace();
			return "";
		}
	}
}
