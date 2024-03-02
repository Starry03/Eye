package Test2;
import Eye.Security.Cors;
import Eye.Server;
public class Main {
	public static void main(String[] args) {
		Server server = new Server(3001);
		server.setCors(new Cors());
		server.addRoute(new Root());
		server.run();
	}
}
