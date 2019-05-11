package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class ServerThread extends Thread {
	
	Socket socket;
	BufferedReader input;
	PrintStream output;
	ObjectInputStream objectInput;
	ObjectOutputStream objectOutput;
	String attemptUID = "";
	int attemptCount = 0;
	boolean stopped = false;
	
	DatabaseConnector database;

	//private SQLReader SQL = new SQLReader();

	public ServerThread(Socket inputSocket, DatabaseConnector database) {
		this.socket = inputSocket;
		this.database = database;
		try {
			this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.output = new PrintStream(socket.getOutputStream());
			// this.objectInput = new ObjectInputStream(socket.getInputStream());
			this.objectOutput = new ObjectOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Server Thread for " + socket.getInetAddress() + " done loading.");
	}
	
	public void run() {
		while (!stopped) {
			exchangeData();
			
			try {
				socket.close();
				stopped = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void exchangeData() {
		System.out.println("Waiting for input.");
		String message = waitForInput();
		System.out.print("Received: " + message);
		String args[] = message.split(";");
		switch(Integer.parseInt(args[0])){
		
			case 0: // ESP requests data about his room.
				System.out.println(". A request for a data packet.");
				
				// Get the data from the database and return it to the ESP.
				String data = database.getDataPacket(args[1]);
				output.println(data);
			break;
				
			case 1: // A card has been scanned.
				System.out.println(". Checking if the card that was scanned is the reserver's.");
				
				if (database.isReserver(args[1], args[2])){
					output.println(1); // The scanned card belonged to the reserver.
				} else {
					output.println(0); // The scanned card didn't belong to the reserver.
				}
			break;
				
				
			case 2: // Reservation has expired due to not checking in.
				System.out.println(". Expiring the thinges due to not checking in.");
				
				System.out.println(". A request for a data packet.");
				
				database.clearRoom(args[1]); // Makes the room available again.
			break;
		}
	}	
	
	private String waitForInput() {
		while(true) {
			try {
				if(input.ready()) {
					return input.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
