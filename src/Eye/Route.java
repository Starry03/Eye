package Eye;

import java.io.IOException;

public abstract class Route {
	protected final String path;
	protected RequestHandler requestHandler;

	public Route(String path) {
		this.path = path;
	}

	/**
	 * This method is called by the endpoint thread to get the response
	 */
	public String getResponse() throws IOException {
		return response();
	}

	/**
	 * Override this method to return a response
	 */
	protected String response() throws IOException {
		return "Default eye response";
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
