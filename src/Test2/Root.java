package Test2;

import Eye.Route.Route;

import java.io.IOException;

public class Root extends Route {
	public Root() {
		super("/");
	}

	@Override
	protected String response() throws IOException {
		return "Hello, World!";
	}
}
