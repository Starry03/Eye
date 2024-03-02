package Eye.Route;

import Eye.Response.Response;
import Eye.Response.UNAUTHORIZED;

import java.io.IOException;

public class ProtectedRoute extends Route {
	private boolean isProtected = true;

	/**
	 * Class made to provide secure access to the route
	 * When a request arrives, the ProtectedRoute class checks for user's credentials
	 */
	public ProtectedRoute(String path) {
		super(path);
	}

	@Override
	public final Response getResponse() throws IOException {
		return protectedResponse();
	}

	private Response protectedResponse() throws IOException {
		if (!isProtected) return response();
		else if (isAuthorized())
			return response();
		return new UNAUTHORIZED();
	}

	/**
	 * Override to implement the authorization logic
	 */
	protected boolean isAuthorized() {
		return false;
	}

	protected void setProtected(boolean isProtected) {
		this.isProtected = isProtected;
	}
}
