package main;

import java.io.File;

public class FileLastModified {

	private String fullpath;
	private long date;

	public FileLastModified(String fullpath) {
		this.fullpath = fullpath;
		File file = new File(fullpath);
		this.date = file.lastModified();
	}

	public boolean updateDate() {
		File file = new File(this.fullpath);
		if(this.date!=file.lastModified()) {
			this.date = file.lastModified();
			return true;
		}
		return false;
	}

	public long getDate() {
		return this.date;
	}
}
