package Eye;

// TODO: Implement the ProtectedRoute class
public class ProtectedRoute extends Route {
	/**
	 * Class made to provide secure access to the route
	 * When a request arrives, the ProtectedRoute class checks for user's credentials
	 */
	public ProtectedRoute(String path) {
		super(path);
	}
}
