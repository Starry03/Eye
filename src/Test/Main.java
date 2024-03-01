package Test;

import Eye.Route.Route;
import Eye.Security.Cors;
import Eye.Route.RoutesHandler;
import Eye.Server;

public class Main {
	public static void main(String[] args) {
		final int PORT = 3000;

		Server server = new Server(PORT);
		Route[] routes = new Route[]{
				new Root(),
				new Datas()
		};
		server.addRoutes(routes);

		server.setCors(new Cors(
				new String[]{
						"http://localhost:3000",
						"https://localhost:3000",
						"http://127.0.0.1:3000",
						"https://127.0.0.1:3000"
				},
				new String[]{"GET", "POST"},
				new String[]{"*"}
		));
		Server.setRootPath("src/Test/");
		Thread serverThread = new Thread(server);
		serverThread.start();
	}
}