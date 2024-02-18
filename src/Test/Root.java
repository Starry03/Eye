package Test;

import Eye.Local.FileManager;
import Eye.Response.HTML;
import Eye.Route.Route;

import java.io.IOException;

public class Root extends Route {
	public Root() {
		super("/");
	}

	@Override
	protected String response() throws IOException {
		HTML response = new HTML(FileManager.GetFileContent("index.html"));
		return response.getResponse();
	}
}
