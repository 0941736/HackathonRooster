import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DatabaseConnector{
	private Connection conn;
	
	public DatabaseConnector() throws SQLException, ClassNotFoundException{
		//bij het aanmaken wordt er connectie met de database gemaakt
		Class.forName("com.mysql.jdbc.Driver");
		
		//verbind met een lokaal draaiende database
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ClassroomRadar?autoReconnect=true&useSSL=false","root","ke3GFsJm!");
		System.out.println("Database: Connected");
	}
	
	public Datapacket getDataPacket(String roomNr)
	{
		Statement send = conn.createStatement();
		ResultSet volumeResultSet = send.executeQuery("");
		return null;
	}
	
	public String getReservationUID(String roomNr)
	{
		Statement send = conn.createStatement();
		ResultSet volumeResultSet = send.executeQuery("");
		return "08347823";
	}
	
	public void clearRoom(String roomNr)
	{
		return;
	}
	
	public boolean write(String mokID, String volume){
		try{
			//maak een query en verstuur die naar de database
			Statement send = conn.createStatement();
			
			//stuur het ID van de mok en het huidige volume naar de server.
			send.executeUpdate("INSERT INTO volumes VALUES(null , '" + mokID + "', " + volume + ", now())");
			System.out.println("Database: Written");
			
			return true;
		} catch (Exception e){

			e.printStackTrace();
			return false;
		}
	}
	
	public ResultSet read(String mokID){
		try{
			//maak een query en verstuur die naar de database
			Statement send = conn.createStatement();
			//Vraag de 100 nieuwste data entries op van het geven mokID
			ResultSet volumeResultSet = send.executeQuery("Select milliliters, time from volumes where mokId = '" + mokID + "' order by time desc limit 100");
			System.out.println("Database: Read");
			
			return volumeResultSet;
		} catch (Exception e){
			
			e.printStackTrace();
			return null;
		}
	}
}