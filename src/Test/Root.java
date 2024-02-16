package Test;

import Webserver.Response.HTML;
import Webserver.Local.LocalUtils;
import Webserver.Route;

import java.io.IOException;

public class Root extends Route {
	public Root() {
		super("/");
	}

	@Override
	public String response() throws IOException {
		HTML response = new HTML(LocalUtils.GetFileContent("src/Test/index.html"));
		return response.getResponse();
	}
}
