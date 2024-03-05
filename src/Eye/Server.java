package Eye;

import Eye.Route.Route;
import Eye.Security.Cors;
import Eye.Logger.Logger;
import Eye.Route.RoutesHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.util.concurrent.LinkedBlockingQueue;

public class Server implements Runnable {
	public static int DEFAULT_PORT = 3000;
	private int requestsRunning = 0;
	private static final int REQUESTS_RUNNING_LIMIT = 100;
	private static final int REQUEST_QUEUE_LIMIT = 500;
	private final LinkedBlockingQueue<Socket> requestsQueue = new LinkedBlockingQueue<>(Server.REQUEST_QUEUE_LIMIT);
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
			this.serverSocket = new ServerSocket(Server.DEFAULT_PORT);
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
		Socket currentSocket;
		Socket queuedSocket;
		EndpointThread endpointThread;
		Thread thread;
		while (running) {
			Logger.info("Requests running: " + requestsRunning);
			try {
				currentSocket = serverSocket.accept();
				Logger.info("Request arrived");
				if (requestsRunning >= Server.REQUESTS_RUNNING_LIMIT) {
					requestsQueue.add(currentSocket);
					Logger.info("Request queued");
					continue;
				}
				queuedSocket = requestsQueue.poll();
				if (queuedSocket != null) {
					requestsQueue.add(currentSocket);
					currentSocket = queuedSocket;
				}
				endpointThread = new EndpointThread(
						this,
						currentSocket
				);
				if (!running) break;
				thread = new Thread(endpointThread);
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

	public void addRequestRunning() {
		requestsRunning++;
	}

	public void removeRequestRunning() {
		requestsRunning--;
	}
}
