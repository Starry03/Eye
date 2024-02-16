package Eye;

import java.io.IOException;

public abstract class Route {
	private String path;

	public Route(String path) {
		this.path = path;
	}

	/**
	 * Override this method to return a response
	 */
	public String response() throws IOException {
		return "";
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

	public void setPath(String path) {
		this.path = path;
	}
}
