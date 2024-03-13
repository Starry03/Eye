package Test;

import Eye.RequestHandler;
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
		// access request handler in the route
		RequestHandler requestHandler = getRequestHandler();
		return new HTML(Path.of("./index.html"));
	}
}
