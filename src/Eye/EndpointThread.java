package Eye;

import Eye.Logger.Logger;
import Eye.Response.ResponseSender;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

	/**
	 * Closes the socket.
	 */
	private void closeConnection() {
		try {
			socket.close();
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}
	}

	/**
	 * This method is called when the thread is started.
	 * It reads the input stream and sends the response to the output stream.
	 */
	@Override
	public synchronized void run() {
		if (executed) return;
		executed = true;
		server.addRequestRunning();
		Scanner scanner = new Scanner(inputStream);
		RequestHandler requestHandler = new RequestHandler(scanner, server, socket);
		String path = requestHandler.getPath();
		ResponseSender.send(path, outputStream, server.getRoutesHandler(), requestHandler);
		closeConnection();
		scanner.close();
		server.removeRequestRunning();
	}

	public synchronized boolean isExecuted() {
		return executed;
	}
}
