package Eye.Response;

public final class CSS extends Response {
	public CSS(String content) {
		super(content, "Content-Type: text/css\r\n");
	}
}
