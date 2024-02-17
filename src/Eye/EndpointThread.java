package Eye;

import Eye.Local.LocalUtils;
import Eye.Logger.Logger;
import Eye.Response.FILE;
import Eye.Response.Response;

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
		Logger.info("connection opened");
		String response;
		Scanner scanner = new Scanner(this.inputStream);
		RequestHandler requestHandler = new RequestHandler(scanner);
		String path = requestHandler.getPath();
		Route route = routesHandler.getRouters().get(path);
		if (route != null) {
			try {
				sendResponse(route.response());
				Logger.info("Reponse sent: " + path);
			} catch (IOException e) {
				Logger.error(e.getMessage());
			}
			closeConnection();
			return;
		}
		Logger.warning("Route not found");
		try {
			byte[] content = LocalUtils.GetBinaryFileContent(rootPath + path);
			FILE f = new FILE(content, "file/unknown");
			sendResponse(f.getByteResponse());
			Logger.info(path + " located and sent");
		} catch (IOException e) {
			Logger.error(e.getMessage());
			Logger.warning("file not found");
			sendResponse(Response.NOT_FOUND);
		}
		closeConnection();
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
