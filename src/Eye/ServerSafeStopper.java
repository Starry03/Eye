package Eye;

import Logger.Logger;

import java.util.Scanner;

class ServerSafeStopper implements Runnable {
	private boolean executed;
	private final Server server;
	public ServerSafeStopper(Server server) {
		this.server = server;
	}

	public synchronized void run() {
		if (executed) return;
		executed = true;
		Logger.info("Press a key to stop the server");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		Logger.info("Server will stop at the next request\n");
		server.setRunning(false);
	}

	public synchronized boolean isExecuted() {
		return executed;
	}
}
