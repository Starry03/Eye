package Test2;

import Eye.Response.HTML;
import Eye.Route.Route;

import java.io.IOException;

public class Root extends Route {
	public Root() {
		super("/");
	}

	@Override
	protected String response() throws IOException {
		HTML html = new HTML("Hello, World!");
		String res = html.getResponse();
		System.out.println(res);
		return res;
	}
}
