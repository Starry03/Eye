package Test;

import Eye.Response.Response;
import Eye.Server;
import Eye.Local.FileManager;
import Eye.Response.HTML;
import Eye.Route.Route;

import java.io.IOException;

public class Root extends Route {
	public Root() {
		super("/");
	}

	@Override
	protected Response response() throws IOException {
		return new HTML(FileManager.GetFileContent(Server.getRootPath() + "/index.html"));
	}
}
