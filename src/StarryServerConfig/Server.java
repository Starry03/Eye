package StarryServerConfig;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

	private final ServerSocket serverSocket;
	private final RoutesHandler routesHandler;

	public Server(int port, RoutesHandler routesHandler) throws RuntimeException {
		this.routesHandler = routesHandler;
		try {
			this.serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void run() {
		while (true) {
			try {
				EndpointThread endpointThread = new EndpointThread(
						serverSocket.accept(),
						routesHandler
				);
				Thread thread = new Thread(endpointThread);
				thread.start();
			} catch (IOException exception) {
				System.out.println(exception.getMessage());
			}
		}
	}

	public RoutesHandler getRoutesHandler() {
		return routesHandler;
	}

	public int getPort() {
		return serverSocket.getLocalPort();
	}
}
