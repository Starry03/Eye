package Webserver.Response;


import java.nio.charset.StandardCharsets;

public abstract class Response {
	// private static final String CSP = "Content-Security-Policy: default-src 'self' http://localhost:7777;\r\n";
	protected static final String POSITIVE_RESPONSE = "HTTP/1.1 200 OK\r\n";
	protected static final String SERVER_ERROR = "HTTP/1.1 500 Internal Server Error\r\n";
	protected String content;
	protected String contentType;
	protected int contentLength;
	protected Response(String content, String contentType) {
		this.content = content;
	}

	protected int getContentLength(String content) {
		return content.getBytes(StandardCharsets.UTF_8).length;
	}

	public String getResponse() {
		if (content == null)
			return SERVER_ERROR;

		return POSITIVE_RESPONSE +
				contentType +
				"Content-Length: " + getContentLength(content) + "\r\n" +
				"\r\n" +
				content;
	}
}
