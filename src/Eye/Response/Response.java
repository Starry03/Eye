package Eye.Response;

import Eye.Logger.Logger;

import java.nio.charset.StandardCharsets;

public abstract class Response {
	public static final String OK = "HTTP/1.1 200 OK\r\n";
	public static final String BAD_REQUEST = "HTTP/1.1 400 Bad Request\r\n";
	public static final String UNAUTHORIZED = "HTTP/1.1 401 Unauthorized\r\n";
	public static final String NOT_FOUND = "HTTP/1.1 404 Not Found\r\n";
	public static final String SERVER_ERROR = "HTTP/1.1 500 Internal Server Error\r\n";
	protected String content;
	protected String contentType;
	protected int contentLength;

	protected Response(String content, String contentType) {
		this.content = content;
		this.contentType = contentType;
	}

	protected final int getContentLength() {
		return content.getBytes(StandardCharsets.UTF_8).length;
	}

	protected final String getEmptyResponse() {
		return OK +
				contentType +
				"Content-Length:" +
				getContentLength() +
				"\r\n" +
				"\r\n";
	}

	public String getResponse() {
		if (content == null || content.isEmpty()) {
			Logger.error("Response content is null or empty");
			return NOT_FOUND;
		}

		return getEmptyResponse() + content;
	}

	public String getContent() {
		return content;
	}

	public String getContentType() {
		return contentType;
	}

	@Override
	public String toString() {
		return "Response{" +
				"content='" + content + '\'' +
				", contentType='" + contentType + '\'' +
				", contentLength=" + contentLength +
				'}';
	}
}
