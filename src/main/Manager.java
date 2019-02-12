package main;

public class Manager {

	private Thread thread1;
	private Thread thread2;

	public Manager() {
		thread1 = new Thread() {
			public void run() {
				new FileController();
			}
		};

		thread2 = new Thread() {
			public void run() {
				new TCPServer();
			}
		};

		thread1.start();
		thread2.start();
	}

	public static void main(String[] args) {
		new Manager();
	}
}
