package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
	boolean stopped = false;

	public static void main(String[] args) {
		new ServerMain();
	}

	public ServerMain() {
		try {
			ServerSocket server = new ServerSocket(6789, 100); 
			while (!stopped) {
				System.out.println("Waiting...");
				Socket socket = server.accept();
				System.out.println("Connected with: " + socket.getInetAddress());
				new ServerThread(socket).start();
			}
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}