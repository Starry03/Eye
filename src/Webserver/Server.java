package Webserver;

import Webserver.Logger.Logger;

import java.io.IOException;
import java.net.ServerSocket;

public class Server implements Runnable {
	private boolean executed = false;
	private boolean running = true;
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

	@Override
	public synchronized void run() {
		int count = 0;
		if (executed) return;
		executed = true;
		ServerSafeStopper serverSafeStopper = new ServerSafeStopper(this);
		Thread serverSafeStopperThread = new Thread(serverSafeStopper);
		serverSafeStopperThread.start();
		Logger.info("server started");
		while (running) {
			if (count > 1000) count = 0;
			try {
				EndpointThread endpointThread = new EndpointThread(
						serverSocket.accept(),
						routesHandler
				);
				Thread thread = new Thread(endpointThread);
				thread.setName("EndpointThread: " + count++);
				thread.start();
			} catch (IOException exception) {
				Logger.error(exception.getMessage());
			}
		}
		Logger.info("Server shutted down");
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
}
