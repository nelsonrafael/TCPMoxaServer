package main;

import java.io.*;

public class ParseFile {

	private String fullpath;
	private String ordprod;
	private String material;
	private String ultoper;
	private String qtd;
	private String desct;
	private String oneLiner;
	private boolean singleLine;

	public ParseFile(String fpath) {
		this.fullpath = fpath;
		File file = new File(fullpath);
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			this.singleLine = false;
			this.oneLiner = null;
			String line, temp;
			int i = 0;
			while ((line = br.readLine()) != null) {
				if (i == 6) {
					this.ordprod = line.substring(1, 10);
					this.material = line.substring(25, 31);
					temp = line.substring(31, line.length());
					for (int j = 0; j < 5; j++) {
						temp = temp.substring(temp.indexOf("|") + 1, temp.length());
					}
					this.ultoper = temp.substring(0, temp.indexOf("|"));
					temp = temp.substring(temp.indexOf("|") + 1, temp.length());
					this.qtd = temp.substring(0, temp.indexOf(","));
					temp = temp.substring(temp.indexOf("|") + 1, temp.length());
					this.desct = temp.substring(0, temp.indexOf("|"));
					temp = "<html>";
					for (int j = 0; j < 4; j++) {
						if (this.desct.length() > 43 && j != 3) {
							temp += this.desct.substring(0, 43) + "<br>";
							this.desct = this.desct.substring(43, this.desct.length());
						} else if (this.desct.length() > 43) {
							temp += this.desct.substring(0, 43);
						} else {
							temp += this.desct.substring(0, this.desct.length());
							break;
						}
					}
					temp += "</html>";
					this.desct = temp;
					// System.out.println(temp);
				}
				i++;
				if (i == 1) {
					this.oneLiner = line;
				}
			}
			if (i < 6) {
				// System.out.println(line);
				this.singleLine = true;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		new ParseFile(Miscellaneous.OUTPUT_DIR + Miscellaneous.OUTPUT_FILE);
	}

	public String get_ordprod() {
		return this.ordprod;
	}

	public String get_material() {
		return this.material;
	}

	public String get_ultoper() {
		return this.ultoper;
	}

	public String get_qtd() {
		return this.qtd;
	}

	public String get_desct() {
		return this.desct;
	}

	public String get_oneLiner() {
		return this.oneLiner;
	}

	public boolean get_singleLine() {
		return this.singleLine;
	}
}
