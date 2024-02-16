package Eye;

import Eye.Local.LocalUtils;
import Eye.Logger.Logger;
import Eye.Response.Response;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

final class EndpointThread implements Runnable {
	private boolean executed = false;
	private final RoutesHandler routesHandler;
	private final Socket socket;
	private final OutputStream outputStream;
	private final InputStream inputStream;
	private final String rootPath;

	public EndpointThread(Socket socket, RoutesHandler routesHandler, String rootPath) throws IOException {
		this.socket = socket;
		this.outputStream = socket.getOutputStream();
		this.inputStream = socket.getInputStream();
		this.routesHandler = routesHandler;
		this.rootPath = rootPath;
	}

	private void closeConnection() {
		try {
			socket.close();
			Logger.info("connection closed");
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}
	}

	private String requestLog(RequestHandler requestHandler, String response) {
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
		String response;
		try {
			Scanner scanner = new Scanner(this.inputStream);
			RequestHandler requestHandler = new RequestHandler(scanner);
			String path = requestHandler.getPath();
			Route route = routesHandler.getRouters().get(path);
			if (route != null) {
				sendResponse(route.response());
				return;
			}
			Logger.error("Route not found");
			try {
				byte[] content = LocalUtils.GetBinaryFileContent(rootPath + path);
				sendResponse(content);
			} catch (IOException e) {
				Logger.error(e.getMessage());
				sendResponse(Response.NOT_FOUND);
			}
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}
	}

	private void sendResponse(byte[] response) throws IOException {
		outputStream.write(response);
		outputStream.flush();
		closeConnection();
	}

	private void sendResponse(String response) throws IOException {
		sendResponse(response.getBytes(StandardCharsets.UTF_8));
	}

	public synchronized boolean isExecuted() {
		return executed;
	}
}
