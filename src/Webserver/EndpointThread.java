package Webserver;

import Webserver.Logger.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class EndpointThread implements Runnable {
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
			System.out.println(e.getMessage());
		}
	}

	private String requestLog(RequestHandler requestHandler) {
		return "Request from: " + requestHandler.getHost() + "\n" +
				"Time: " + requestHandler.getTime() + "\n" +
				"Path: " + requestHandler.getPath() + "\n" +
				"Parameters: " + requestHandler.getQueryParams() + "\n";
	}

	@Override
	public void run() {
		PrintWriter out;
		RequestHandler requestHandler;
		try {
			out = new PrintWriter(socket.getOutputStream());
			requestHandler = new RequestHandler(socket.getInputStream());
			String path = requestHandler.getPath();
			String response = "";
			for (Route router : routesHandler.routers) {
				if (router.getPath().equals(path)) {
					response = router.response();
					out.write(response);
					out.flush();
					break;
				}
			}
			closeConnection(out);
			Logger.info(requestLog(requestHandler));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
