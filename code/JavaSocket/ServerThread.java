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
	
	DatabaseConnector database = new DatabaseConnector();

	//private SQLReader SQL = new SQLReader();

	public ServerThread(Socket inputSocket) {
		this.socket = inputSocket;
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
		while (true) {
			exchangeData();
		}
	}
	
	public void exchangeData() {
		System.out.println("Waiting for input.");
		String message = waitForInput();
		String args[] = message.split(";");
		switch(Integer.parseInt(args[0])){
		
			case 0: // ESP requests data about his room.
				// Get the data from the database and return it to the ESP.
				String data = database.getDataPacket(args[1]);
				output.println(data);
			break;
				
			case 1: // A card has been scanned.
				if (database.isReserver(args[1], args[2])){
					output.println(1); // The scanned card belonged to the reserver.
				} else {
					output.println(0); // The scanned card didn't belong to the reserver.
				}
			break;
				
				
			case 2: // Reservation has expired due to not checking in.
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
