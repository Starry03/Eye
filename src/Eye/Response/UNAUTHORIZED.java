package Eye.Response;

public class UNAUTHORIZED extends Response{
	public UNAUTHORIZED() {
		super("HTTP/1.1 401 Unauthorized\n", "");
	}
}
