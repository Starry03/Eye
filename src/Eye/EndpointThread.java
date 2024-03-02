package Eye;

import Eye.Logger.Logger;
import Eye.Response.ResponseSender;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

class EndpointThread implements Runnable {
	private boolean executed = false;
	private final Server server;
	private final Socket socket;
	private final OutputStream outputStream;
	private final InputStream inputStream;

	public EndpointThread(Server server, Socket socket) throws IOException {
		this.server = server;
		this.socket = socket;
		this.outputStream = socket.getOutputStream();
		this.inputStream = socket.getInputStream();
	}

	private void closeConnection() {
		try {
			socket.close();
			Logger.info("End: connection closed\n");
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}
	}

	@Override
	public synchronized void run() {
		if (executed) return;
		executed = true;
		Logger.info("Start: connection opened");
		Scanner scanner = new Scanner(inputStream);
		RequestHandler requestHandler = new RequestHandler(scanner, server);
		String path = requestHandler.getPath();
		ResponseSender.send(path, outputStream, server.getRoutesHandler(), requestHandler);
		closeConnection();
		scanner.close();
	}

	public synchronized boolean isExecuted() {
		return executed;
	}
}
