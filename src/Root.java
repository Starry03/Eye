import StarryServerConfig.Response;
import StarryServerConfig.Route;

public class Root extends Route {
	public Root() {
		setPath("/");
	}

	@Override
	public String response() {
		return Response.HTML(GetFileContent("src/index.html"));
	}
}
