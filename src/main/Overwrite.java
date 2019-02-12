package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Overwrite {
	public Overwrite(String text) {
		File file = new File(Miscellaneous.OUTPUT_DIR + Miscellaneous.OUTPUT_FILE);
		try {
			FileOutputStream output = new FileOutputStream(file, false);
			byte[] mybytes = text.getBytes();
			try {
				output.write(mybytes);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
