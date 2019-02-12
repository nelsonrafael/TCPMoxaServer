package main;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

public class FileController {

	private FileLastModified fileDate = null;
	private ParseFile fileParse;

	public FileController() {
		Timer timer = new Timer();
		timer.schedule(new Verifier(), 0, 5000);
	}

	class Verifier extends TimerTask {
		private Socket socket;
		private DataOutputStream streamOut;

		public void run() {
			fileParse = null;
			String message;
			File f = new File(Miscellaneous.OUTPUT_DIR + Miscellaneous.OUTPUT_FILE);
			if (f.exists() && !f.isDirectory()) {
				if (fileDate == null) {
					fileDate = new FileLastModified(Miscellaneous.OUTPUT_DIR + Miscellaneous.OUTPUT_FILE);
					fileParse = new ParseFile(Miscellaneous.OUTPUT_DIR + Miscellaneous.OUTPUT_FILE);
				} else if (fileDate.updateDate()) {
					fileParse = new ParseFile(Miscellaneous.OUTPUT_DIR + Miscellaneous.OUTPUT_FILE);
				}
				if (fileParse != null) {
					if (fileParse.get_singleLine()) {
						message = fileParse.get_oneLiner();
						if (message.trim().length() > 0) {
							message = message.trim();
						}
					} else {
						message = fileParse.get_ordprod() + "<>" + fileParse.get_material() + "<>" + fileParse.get_qtd()
								+ "<>" + fileParse.get_ultoper() + "<>" + fileParse.get_desct();
					}
					System.out.println(message);
					try {
						this.socket = new Socket(Miscellaneous.DISPLAY_IP, Miscellaneous.DISPLAY_PORT);
						this.streamOut = new DataOutputStream(socket.getOutputStream());
						// System.out.println("FileController connected: " + socket);

					} catch (UnknownHostException e) {
						System.out.println("FileController 53");
						e.printStackTrace();
					} catch (IOException e) {
						System.out.println("FileController 56");
						e.printStackTrace();
					}
					try {
						streamOut.writeUTF(message);
						streamOut.flush();
					} catch (IOException e) {
						System.out.println("FileController 63");
						e.printStackTrace();
					}
					try {
						this.socket.close();
						// System.out.println("FileController closed: " + socket);
					} catch (IOException e) {
						System.out.println("FileController 70");
						e.printStackTrace();
					}
				}
			}
		}
	}
}
