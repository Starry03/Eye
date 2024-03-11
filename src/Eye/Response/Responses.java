package Eye.Response;

public abstract class Responses {
	public static final String OK = "HTTP/1.1 200 OK\r\n";
	public static final String BAD_REQUEST = "HTTP/1.1 400 Bad Request\r\n";
	public static final String UNAUTHORIZED = "HTTP/1.1 401 Unauthorized\r\n";
	public static final String FORBIDDEN = "HTTP/1.1 403 Forbidden\r\n";
	public static final String NOT_FOUND = "HTTP/1.1 404 Not Found\r\n";
	public static final String SERVER_ERROR = "HTTP/1.1 500 Internal Server Error\r\n";
}
