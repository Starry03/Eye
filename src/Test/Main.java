package Test;

import Eye.RoutesHandler;
import Eye.Server;

public class Main {
	public static void main(String[] args) {
		int PORT = 7777;

		RoutesHandler routesHandler = new RoutesHandler();
		routesHandler.addRouter(new Root());
		routesHandler.addRouter(new Datas());
		Server server = new Server(PORT, routesHandler);
		Thread serverThread = new Thread(server);
		serverThread.start();
	}
}