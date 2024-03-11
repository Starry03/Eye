package Test;

import Eye.Route.Route;
import Eye.Security.Cors;
import Eye.Server;

public class Main {
	public static void main(String[] args) {
		final int PORT = 3000;

		Server server = new Server(PORT);
		Route[] routes = new Route[]{
				new Root()
		};
		server.addRoutes(routes);
		server.setCors(new Cors(
				new String[]{"localhost:3000"},
				new String[]{"GET", "POST", "OPTIONS"},
				new String[]{"*"}
		));
		Server.setRootPath("src/Test/");
		Thread serverThread = new Thread(server);
		serverThread.start();
	}
}