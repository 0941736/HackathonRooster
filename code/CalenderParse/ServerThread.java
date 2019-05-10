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
		String request;
		while (true) {
			try {
				// Receive request string
				request = exchangeData();
				//System.out.println(request);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Stop listening to client");
				return;
			}
		}
	}
	public String exchangeData() throws IOException {
		System.out.println("Waiting for input.");
		String message = "";
		while (true) {
			message = input.readLine();
			//output.println("HI FROM SERVER");
			switch(parseInt(message){
				//data has been requested
				case 0:
					//get data from database
					Datapacket data = database.getDataPacket(currentRoom);
					String eventBeginTime = data.getBeginTime();
					String eventEndTime = data.getEndTime();
					String currentTime = //TODO
					String course = data.getCourse();
					String reserver = data.getReserver();
					
					output.println(eventBeginTime + ";" +  eventEndTime + ";" + currentTime + ";" + course + ";" + reserver)
				break;
				
				//A card has been scanned
				case 1:
					//substring the UID
					String scannedUID = //TODO
					
					string reservationUID = database.getReservationUID(currentRoom);
					if (reservationUID == scannedUID){
						output.println(1);
					} else {
						output.println(0);
					//get card UID corresponding to reservation
				break;
				//Reservation has timed out
				case 2:
					//make room available
					database.clearRoom(currentRoom);
				break;
			}
			if (!message.equals("")) {
				System.out.println(message);
				return message;
			}
		}
	}
}
