import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

	public static void main(String[] args) {
		ServerMain server = new ServerMain();
	}

	public ServerMain() {
		try {
			ServerSocket server = new ServerSocket(6789, 100); 
			while (true) {
				System.out.println("Waiting...");
				Socket socket = server.accept();
				System.out.println("Connected with: " + socket.getInetAddress());
				new ServerThread(socket).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}