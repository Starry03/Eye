package Test;

import Eye.Response.Response;
import Eye.Response.HTML;
import Eye.Route.Route;

import java.io.IOException;
import java.nio.file.Path;

public class Root extends Route {
	public Root() {
		super("/");
	}

	@Override
	protected Response response() throws IOException {
		return new HTML(Path.of("./index.html"));
	}
}
