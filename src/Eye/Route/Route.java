package Eye.Route;

import Eye.RequestHandler;
import Eye.Response.JSON;
import Eye.Response.Response;

import java.io.IOException;

public abstract class Route {
	private final String path;
	private RequestHandler requestHandler;

	public Route(String path) {
		this.path = path;
	}

	/**
	 * This method is called by the endpoint thread to get the response
	 */
	public Response getResponse() throws IOException {
		return response();
	}

	/**
	 * Override this method to return a response
	 */
	protected Response response() throws IOException {
		return new JSON("{default_eye: 'hi'}");
	}

	@Override
	public String toString() {
		return "Route{" +
				"path='" + path + '\'' +
				'}';
	}

	public String getPath() {
		return path;
	}

	public RequestHandler getRequestHandler() {
		return requestHandler;
	}

	/**
	 * The endpoint thread will call ResponseSender.sendRouteResponse, which will call this method to set the requestHandler
	 * and make your custom route able to access the requestHandler
	 */
	public void setRequestHandler(RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}
}
