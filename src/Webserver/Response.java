package Webserver;

import java.nio.charset.StandardCharsets;

public abstract class Response {
	// private static final String CSP = "Content-Security-Policy: default-src 'self' http://localhost:7777;\r\n";
	private static final String POSITIVE_RESPONSE = "HTTP/1.1 200 OK\r\n";
	private static final String SERVER_ERROR = "HTTP/1.1 500 Internal Server Error\r\n";

	public static String HTML(String html) {
		if (html == null)
			return SERVER_ERROR;
		return POSITIVE_RESPONSE +
				Content.HTML +
				"StarryServerConfig.Content-Length: " + html.getBytes(StandardCharsets.UTF_8).length + "\r\n" +
				"\r\n" +
				html;
	}

	public static String JSON(String json) {
		if (json == null)
			return SERVER_ERROR;
		return POSITIVE_RESPONSE +
				Content.JSON +
				"\r\n" +
				json;
	}
}
