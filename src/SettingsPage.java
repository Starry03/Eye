import StarryServerConfig.Response;
import StarryServerConfig.Route;

public class SettingsPage extends Route {
	public SettingsPage() {
		setPath("/settings");
	}

	@Override
	public String response() {
		return Response.HTML("<h1>Settings Page</h1>");
	}
}
