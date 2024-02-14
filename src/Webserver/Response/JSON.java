package Webserver.Response;

public class JSON extends Response {
	public JSON(String content) {
		super(content, "Content-Type: application/json\r\n");
	}
}
