import Webserver.Response.HTML;
import Webserver.Route;

public class Root extends Route {
	public Root() {
		super("/");
	}

	@Override
	public String response() {
		HTML response = new HTML(GetFileContent("src/index.html"));
		return response.getResponse();
	}
}
