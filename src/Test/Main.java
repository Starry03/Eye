package Test;

import Eye.Cors;
import Eye.Route.RoutesHandler;
import Eye.Server;

public class Main {
	public static void main(String[] args) {
		final int PORT = 3000;

		RoutesHandler routesHandler = new RoutesHandler();
		routesHandler.addRoute(new Root());
		routesHandler.addRoute(new Datas());
		Server server = new Server(PORT, routesHandler);
		server.setCors(new Cors(
				new String[]{"*"},
				new String[]{"GET", "POST"},
				new String[]{"*"}
		));
		Server.setRootPath("src/Test/");
		Thread serverThread = new Thread(server);
		serverThread.start();
	}
}