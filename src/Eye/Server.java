package Eye;

import Eye.Logger.Logger;
import Eye.Route.Route;
import Eye.Route.RoutesHandler;
import Eye.Security.Cors;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.*;

public class Server implements Runnable {
	public static int DEFAULT_PORT = 3000;
	private int requestsRunning = 0;
	private static int REQUESTS_RUNNING_LIMIT = 5_000;
	private static int REQUEST_QUEUE_LIMIT = 50_000;
	private boolean executed = false;
	private boolean running = true;
	private final ServerSocket serverSocket;
	private final RoutesHandler routesHandler = new RoutesHandler();
	private static Path rootPath = Path.of("./");
	private Cors cors = new Cors();
	private final Semaphore mutex = new Semaphore(1);

	/**
	 * @param port server port
	 * @throws RuntimeException if server cannot be created
	 */
	public Server(int port) throws RuntimeException {
		try {
			this.serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @throws RuntimeException if server cannot be created
	 */
	public Server() throws RuntimeException {
		try {
			this.serverSocket = new ServerSocket(Server.DEFAULT_PORT);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public synchronized void run() {
		if (executed)
			return;
		executed = true;
		LinkedBlockingQueue<Socket> requestsQueue = new LinkedBlockingQueue<>(Server.REQUEST_QUEUE_LIMIT);
		ServerSafeStopper serverSafeStopper = new ServerSafeStopper(this);
		Thread serverSafeStopperThread = new Thread(serverSafeStopper);
		serverSafeStopperThread.start();
		Socket currentSocket;
		Logger.info("Server started");
		Logger.info("Port: " + getPort());
		while (running) {
			try {
				evalQueuedRequests(requestsQueue);
				currentSocket = serverSocket.accept();
				if (!running)
					break;
				if (requestsRunning >= Server.REQUESTS_RUNNING_LIMIT)
					// trashes the socket if the queue is full
					if (!requestsQueue.offer(currentSocket))
						continue;
				runEndpointThread(currentSocket);
			} catch (IOException | NullPointerException exception) {
				Logger.error(exception.getMessage());
			}
		}
		Logger.info("Server stopped\n");
	}

	private void evalQueuedRequests(LinkedBlockingQueue<Socket> queue) throws IOException {
		Socket socket;
		while (requestsRunning < Server.REQUESTS_RUNNING_LIMIT && (socket = queue.poll()) != null)
			runEndpointThread(socket);
	}

	private void runEndpointThread(Socket socket) throws IOException {
		EndpointThread endpointThread = new EndpointThread(
				this,
				socket);
		Thread thread = new Thread(endpointThread);
		thread.start();
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

	/**
	 * 
	 * @param running
	 * @implNote thread-safe
	 */
	public void setRunning(boolean running) {
		try {
			mutex.acquire();
			this.running = running;
			mutex.release();
		} catch (InterruptedException e) {
			Logger.error(e.getMessage());
		}
	}

	public static Path getRootPath() {
		return rootPath;
	}

	public static void setRootPath(String path) {
		Server.rootPath = Path.of(path);
	}

	/**
	 * Adds route
	 *
	 * @param route path
	 */
	public void addRoute(Route route) {
		routesHandler.addRoute(route);
	}

	/**
	 * Adds array of routes
	 *
	 * @param route routes array
	 */
	public void addRoutes(Route[] route) {
		routesHandler.addRoutes(route);
	}

	public Cors getCors() {
		return cors;
	}

	public void setCors(Cors cors) {
		this.cors = cors;
	}

	/**
	 * Adds request running
	 * 
	 * @implNote thread-safe
	 */
	public void addRequestRunning() {
		try {
			mutex.acquire();
			requestsRunning++;
			mutex.release();
		} catch (InterruptedException e) {
			Logger.error(e.getMessage());
		}
	}

	/**
	 * Removes request running
	 * 
	 * @implNote thread-safe
	 */
	public void removeRequestRunning() {
		try {
			mutex.acquire();
			requestsRunning--;
			mutex.release();
		} catch (InterruptedException e) {
			Logger.error(e.getMessage());
		}
	}

	public void setRequestsRunningLimit(int limit) {
		Server.REQUESTS_RUNNING_LIMIT = limit;
	}

	public void setRequestQueueLimit(int limit) {
		Server.REQUEST_QUEUE_LIMIT = limit;
	}
}
