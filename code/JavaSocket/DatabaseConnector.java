package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DatabaseConnector{
	private Connection conn;
	private UIDConverter uidCon;
	
	public DatabaseConnector() {
		// Get the Driver needed for the Database.
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// Connect to the locally running database.
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ClassroomRadar","gewad","gewad");
			System.out.println("Connected to the Reservation Database.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getDataPacket(String roomNr) {
		Statement statement;
		String returnString;
		try {
			statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT NOW(), begin, end, subject, reserver FROM ClassroomRadar.reservation\r\n" + 
					"WHERE end > NOW() && reservation.room = \r\n" + 
					"(SELECT id FROM ClassroomRadar.room WHERE code = '" + roomNr + "')");
			
			returnString = getTimeString();
			for(int i = 0; i < 4; i++) {
				if(result.getString(i) == null) {
					return null;
				}
				returnString += ";" + result.getString(i).replaceAll("[- :]", "");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return returnString;
	}
	
	public boolean isReserver(String roomNr, String UID) {
		
		// Retrieve the data about the room from the Database.
		String dataString = this.getDataPacket(roomNr);
		
		// Check if the room is reserved.
		if(dataString == null) {
			// Last room reserve has already passed.
			return false;
		}
		
		// Split the string up so individual argument can be used.
		String args[] = dataString.split(";");
		if(!uidCon.getCode(UID).equalsIgnoreCase(args[3])) {
			// 
			return false;
		}
		
		return true;
	}
	
	public void clearRoom(String roomNr) {
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
					"UPDATE `ClassroomRadar`.`reservation` SET `end` = NOW()\r\n" + 
					"WHERE reservation.room = (SELECT id FROM ClassroomRadar.room WHERE code = '"+ roomNr +"')");
		} catch (SQLException e) {
			return;
		}
		return;
	}
	
	public String getTimeString() {
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(cal.getTime());
	}
}