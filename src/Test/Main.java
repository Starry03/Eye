package Test;

import Eye.Logger.Logger;
import Eye.RoutesHandler;
import Eye.Server;

public class Main {
	public static void main(String[] args) {
		int PORT = 3000;

		RoutesHandler routesHandler = new RoutesHandler();
		routesHandler.addRouter(new Root());
		routesHandler.addRouter(new Datas());
		Server server = new Server(PORT, routesHandler);
		Server.setRootPath("src/Test/");
		Logger.setActive(false);
		Thread serverThread = new Thread(server);
		serverThread.start();
	}
}