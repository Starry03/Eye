package StarryServerConfig;

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
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private String requestLog(RequestHandler requestHandler) {
		return "Request received from " + requestHandler.getHost() +
				" at " + requestHandler.getTime() +
				" for " + requestHandler.getPath();
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

			System.out.println(requestLog(requestHandler));
			closeConnection(out);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
