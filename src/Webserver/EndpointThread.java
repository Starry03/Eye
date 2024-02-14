package Webserver;

import Webserver.Logger.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class EndpointThread implements Runnable {
	private boolean executed;
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

	private String requestLog(RequestHandler requestHandler) {
		return "Request from: " + requestHandler.getHost() + "\n" +
				"Time: " + requestHandler.getTime() + "\n" +
				"Path: " + requestHandler.getPath() + "\n" +
				"Parameters: " + requestHandler.getQueryParams() + "\n";
	}

	@Override
	public synchronized void run() {
		if (executed) return;
		executed = true;
		PrintWriter out;
		RequestHandler requestHandler;
		try {
			out = new PrintWriter(socket.getOutputStream());
			requestHandler = new RequestHandler(socket.getInputStream());
			String path = requestHandler.getPath();
			for (Route router : routesHandler.getRouters())
				if (router.getPath().equals(path)) {
					out.write(router.response());
					out.flush();
					break;
				}
			closeConnection(out);
			Logger.info(requestLog(requestHandler));
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}
	}

	public synchronized boolean isExecuted() {
		return executed;
	}
}
