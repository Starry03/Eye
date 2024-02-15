package Webserver.Response;

public final class JSON extends Response {
	public JSON(String content) {
		super(content, "Content-Type: application/json\r\n");
	}
}
