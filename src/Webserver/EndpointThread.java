package Webserver;

import Webserver.Logger.Logger;
import Webserver.Response.NotFound;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

final class EndpointThread implements Runnable {
	private boolean executed = false;
	private final RoutesHandler routesHandler;
	private final Socket socket;

	public EndpointThread(Socket socket, RoutesHandler routesHandler) {
		this.socket = socket;
		this.routesHandler = routesHandler;
	}

	private void closeConnection(PrintWriter out) {
		try {
			out.close();
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
		PrintWriter out;
		RequestHandler requestHandler;
		String response;
		try {
			out = new PrintWriter(socket.getOutputStream());
			requestHandler = new RequestHandler(socket.getInputStream());
			Logger.info(requestHandler.toString());
			String path = requestHandler.getPath();
			Route route = routesHandler.getRouters().get(path);
			if (route == null) {
				Logger.error("Route not found");
				response = new NotFound().getResponse();
			} else response = route.response();
			out.write(response);
			out.flush();
			closeConnection(out);
			// Logger.info(requestLog(requestHandler, response));
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}
	}

	public synchronized boolean isExecuted() {
		return executed;
	}
}
