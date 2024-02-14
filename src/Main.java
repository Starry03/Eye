import StarryServerConfig.RoutesHandler;
import StarryServerConfig.Server;

public class Main {
	public static void main(String[] args) {
		RoutesHandler routesHandler = new RoutesHandler();
		routesHandler.addRouter(new Root());
		routesHandler.addRouter(new SettingsPage());
		routesHandler.addRouter(new Datas());
		Server server = new Server(7777, routesHandler);
		Thread serverThread = new Thread(server);
		serverThread.start();
	}
}