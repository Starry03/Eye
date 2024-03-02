package Eye.Response;

public final class HTML extends Response {
	public HTML(String content) {
		super(content, "Content-Type: text/html\r\n");
	}
}
