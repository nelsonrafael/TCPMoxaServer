package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	private int serverPort = 55555;
	private ServerSocket serverSocket;

	public static boolean containsOnlyNumbers(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i)))
				return false;
		}
		return true;
	}

	public TCPServer() {
		try {
			serverSocket = new ServerSocket(serverPort);
			System.out.println("Waiting...");
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("Connected: " + socket);
				ConnectionService service = new ConnectionService(socket);
				service.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new TCPServer();
	}

	class ConnectionService extends Thread {

		private Socket socket;
		private BufferedReader inputReader;

		public ConnectionService(Socket socket) {
			this.socket = socket;
			try {
				inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

		@Override
		public void run() {
			while (true) {
				try {
					String receivedMessage = inputReader.readLine();
					String ip = socket.toString().substring(socket.toString().indexOf("/") + 1);
					ip = ip.substring(0, ip.indexOf(","));
					String port = socket.toString().substring(socket.toString().indexOf(",port=") + 6);
					port = port.substring(0, port.indexOf(","));
					java.util.Date date = new java.util.Date();
					System.out.println("------------------------------------------------------------------------");
					System.out.println(ip + ":" + port);
					System.out.println(receivedMessage);
					if (receivedMessage.length() >= 70) {
						if (containsOnlyNumbers(receivedMessage.substring(52, 61))) {
							System.out.println("SEND TO SAP:::" + receivedMessage.substring(52, 61));
						}
					}
					//System.out.println(date);
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}

	}
}
