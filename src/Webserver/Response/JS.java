package Webserver.Response;

public class JS extends Response{
	public JS(String content) {
		super(content, "Content-Type: text/javascript\r\n");
	}
}
