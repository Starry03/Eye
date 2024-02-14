import Webserver.Response;
import Webserver.Route;

public class SettingsPage extends Route {
	public SettingsPage() {
		super("/settings");
	}

	@Override
	public String response() {
		return Response.HTML("<h1>Settings Page</h1>");
	}
}
