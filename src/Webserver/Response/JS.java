package Webserver.Response;

public final class JS extends Response{
	public JS(String content) {
		super(content, "Content-Type: text/javascript\r\n");
	}
}
