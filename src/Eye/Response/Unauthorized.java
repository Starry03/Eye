package Eye.Response;

public final class Unauthorized extends Response {
	/**
	 * Singleton instance
	 */
	private static final Unauthorized unauthorized = new Unauthorized();
	private Unauthorized() {
		super(Response.UNAUTHORIZED, "");
	}

	/**
	 * @return the singleton instance
	 */
	public static Unauthorized getInstance() {
		return unauthorized;
	}
}
