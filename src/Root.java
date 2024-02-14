import Webserver.Response;
import Webserver.Route;

public class Root extends Route {
	public Root() {
		super("/");
	}

	@Override
	public String response() {
		return Response.HTML(GetFileContent("src/index.html"));
	}
}
