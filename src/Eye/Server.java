package Eye;

import Eye.Route.Route;
import Eye.Security.Cors;
import Eye.Logger.Logger;
import Eye.Route.RoutesHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Path;

public class Server implements Runnable {
	public static int DEFAULT_PORT = 3000;
	private boolean executed = false;
	private boolean running = true;
	private final ServerSocket serverSocket;
	private final RoutesHandler routesHandler = new RoutesHandler();
	private static Path rootPath = Path.of("./");
	private Cors cors = new Cors();
	public Server(int port) throws RuntimeException {
		try {
			this.serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Server() throws RuntimeException {
		try {
			this.serverSocket = new ServerSocket(DEFAULT_PORT);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public synchronized void run() {
		if (executed) return;
		executed = true;
		ServerSafeStopper serverSafeStopper = new ServerSafeStopper(this);
		Thread serverSafeStopperThread = new Thread(serverSafeStopper);
		serverSafeStopperThread.start();
		Logger.info("Server started\n" + "Port: " + getPort() + "\n");
		while (running) {
			try {
				EndpointThread endpointThread = new EndpointThread(
						this,
						serverSocket.accept()
				);
				if (!running) break;
				Thread thread = new Thread(endpointThread);
				thread.start();
			} catch (IOException exception) {
				Logger.error(exception.getMessage());
			}
		}
		Logger.info("Server stopped\n");
	}

	public RoutesHandler getRoutesHandler() {
		return routesHandler;
	}

	public int getPort() {
		return serverSocket.getLocalPort();
	}

	public synchronized boolean isExecuted() {
		return executed;
	}

	public synchronized boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public static Path getRootPath() {
		return rootPath;
	}

	public static void setRootPath(String path) {
		Server.rootPath = Path.of(path);
	}

	public void addRoute(Route route) {
		routesHandler.addRoute(route);
	}

	public void addRoutes(Route[] route) {
		routesHandler.addRoutes(route);
	}

	public Cors getCors() {
		return cors;
	}

	public void setCors(Cors cors) {
		this.cors = cors;
	}
}
