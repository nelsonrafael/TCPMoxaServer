package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

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
			serverSocket = new ServerSocket(Miscellaneous.PORT);
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
					boolean foundOrder = false;
					String printMessage = "";
					String receivedMessage = inputReader.readLine();
					String ip = socket.toString().substring(socket.toString().indexOf("/") + 1);
					ip = ip.substring(0, ip.indexOf(","));
					String port = socket.toString().substring(socket.toString().indexOf(",port=") + 6);
					port = port.substring(0, port.indexOf(","));
					java.util.Date date = new java.util.Date();
					// System.out.println("------------------------------------------------------------------------");
					// System.out.println(ip + ":" + port);
					// System.out.println(receivedMessage);
					// printMessage = ip + ":" + port + " " + date + " " + receivedMessage;
					// System.out.println(printMessage);
					if (receivedMessage.length() >= 70) {
						printMessage = ip + ":" + port + " " + date + " "
								+ receivedMessage.substring(5, receivedMessage.length() - 5);
						if (receivedMessage.contains(" 213010 ") || receivedMessage.contains(" 211010 ")
								|| receivedMessage.contains(" 211040 ")) {
							System.out.println(printMessage + " DESENHO COM ETIQUETA SIMPLES");							
							String cutMessage = receivedMessage.substring(5, receivedMessage.length() - 5).trim();
							@SuppressWarnings("unused")
							Overwrite oFile = new Overwrite(cutMessage);
							foundOrder = true;
						} else {
							for (int i = 0; i < receivedMessage.length() - 9; i++) {
								if (containsOnlyNumbers(receivedMessage.substring(i, i + 9))) {
									// System.out.println("SEND TO SAP:::" + receivedMessage.substring(i, i + 9));
									System.out.println(printMessage);
									String command = "python " + Miscellaneous.SAP_SCRIPT + " "
											+ receivedMessage.substring(i, i + 9) + " " + Miscellaneous.OUTPUT_DIR + " "
											+ Miscellaneous.OUTPUT_FILE;
									// System.out.println(command);
									@SuppressWarnings("unused")
									Process p = Runtime.getRuntime().exec(command);
									foundOrder = true;
								}
							}
						}
						if (!foundOrder) {
							System.out.println(printMessage + " ORDEM NÃO ENCONTRADA");
							String cutMessage = receivedMessage.substring(5, receivedMessage.length() - 5).trim();
							@SuppressWarnings("unused")
							Overwrite oFile = new Overwrite(cutMessage + " ORDEM NAO ENCONTRADA");
						}
					}
					// System.out.println(date);
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
}
