package Test;

import Eye.Local.FileManager;
import Eye.Response.HTML;
import Eye.Route;

import java.io.IOException;

public class Root extends Route {
	public Root() {
		super("/");
	}

	@Override
	public String response() throws IOException {
		HTML response = new HTML(FileManager.GetFileContent("index.html"));
		return response.getResponse();
	}
}
