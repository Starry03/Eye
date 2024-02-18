package Eye;

import Eye.Logger.Logger;
import Eye.Response.ResponseSender;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

class EndpointThread implements Runnable {
	private boolean executed = false;
	private final RoutesHandler routesHandler;
	private final Socket socket;
	private final OutputStream outputStream;
	private final InputStream inputStream;

	public EndpointThread(Socket socket, RoutesHandler routesHandler) throws IOException {
		this.socket = socket;
		this.outputStream = socket.getOutputStream();
		this.inputStream = socket.getInputStream();
		this.routesHandler = routesHandler;
	}

	private void closeConnection() {
		try {
			socket.close();
			Logger.info("End: connection closed\n");
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}
	}

	public String getActivity(RequestHandler requestHandler, String response) {
		return "Request from: " + requestHandler.getHost() + "\n" +
				"Time: " + requestHandler.getTime() + "\n" +
				"Path: " + requestHandler.getPath() + "\n" +
				"Parameters: " + requestHandler.getQueryParams() + "\n" +
				"Response: " + response + "\n";
	}

	@Override
	public synchronized void run() {
		if (executed) return;
		executed = true;
		Logger.info("Start: connection opened");
		Scanner scanner = new Scanner(inputStream);
		RequestHandler requestHandler = new RequestHandler(scanner);
		String path = requestHandler.getPath();
		ResponseSender.send(path, outputStream, routesHandler, requestHandler);
		closeConnection();
		scanner.close();
	}

	private void sendResponse(byte[] response) {
		try {
			outputStream.write(response);
			outputStream.flush();
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}
	}

	private void sendResponse(String response) {
		sendResponse(response.getBytes(StandardCharsets.UTF_8));
	}

	public synchronized boolean isExecuted() {
		return executed;
	}
}
