package Webserver.Response;

public final class NotFound extends Response{
	public NotFound() {
		super("404 Not Found", "Content-Type: text/html\r\n");
	}
}
