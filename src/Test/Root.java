package Test;

import Eye.Response.HTML;
import Eye.Local.LocalUtils;
import Eye.Route;

import java.io.IOException;

public class Root extends Route {
	public Root() {
		super("/");
	}

	@Override
	public String response() throws IOException {
		HTML response = new HTML(LocalUtils.GetFileContent("index.html"));
		return response.getResponse();
	}
}
