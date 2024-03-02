package Test2;

import Eye.Response.HTML;
import Eye.Response.Response;
import Eye.Route.Route;

import java.io.IOException;

public class Root extends Route {
	public Root() {
		super("/");
	}

	@Override
	protected Response response() throws IOException {
		return new HTML("Hello, World!");
	}
}
