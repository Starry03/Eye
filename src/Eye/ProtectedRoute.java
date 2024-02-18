package Eye;

import Eye.Response.Response;

import java.io.IOException;

public class ProtectedRoute extends Route {
	protected boolean isProtected = true;

	/**
	 * Class made to provide secure access to the route
	 * When a request arrives, the ProtectedRoute class checks for user's credentials
	 */
	public ProtectedRoute(String path) {
		super(path);
	}

	@Override
	public final String getResponse() throws IOException {
		return protectedResponse();
	}

	private String protectedResponse() throws IOException {
		if (isProtected && !isAuthorized())
			return Response.UNAUTHORIZED;
		return response();
	}

	/**
	 * Override to implement the authorization logic
	 */
	protected boolean isAuthorized() {
		return false;
	}
}
