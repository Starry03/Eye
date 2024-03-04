package Eye.Response;

import Eye.Logger.Logger;
import Eye.RequestHandler;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public abstract class Response {
	public static final String OK = "HTTP/1.1 200 OK\r\n";
	public static final String BAD_REQUEST = "HTTP/1.1 400 Bad Request\r\n";
	public static final String UNAUTHORIZED = "HTTP/1.1 401 Unauthorized\r\n";
	public static final String FORBIDDEN = "HTTP/1.1 403 Forbidden\r\n";
	public static final String NOT_FOUND = "HTTP/1.1 404 Not Found\r\n";
	public static final String SERVER_ERROR = "HTTP/1.1 500 Internal Server Error\r\n";
	protected String content;
	protected String contentType;
	protected Path path;
	protected RequestHandler requestHandler;

	/**
	 * if true, response will be streamed to the client in 1024 byte chunks
	 * it works only for files
	 *
	 * @see Eye.Response.ByteStreamResponse
	 */
	protected boolean stream;
	protected long contentLength;

	protected Response(String content, String contentType) {
		this.content = content;
		this.contentType = contentType;
	}

	protected Response(Path path, boolean stream) {
		this.path = path;
		this.contentType = "application/octet-stream";
		this.stream = stream;
	}

	protected Response(Path path, String contentType, boolean stream) {
		this.path = path;
		this.contentType = contentType;
		this.stream = stream;
	}
	
	protected void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	protected final long getContentLength() {
		return contentLength;
	}

	/**
	 * @return positive response with minimal headers
	 */
	protected final String getEmptyResponse() {
		return OK +
				contentType +
				getContentLengthHeader() +
				requestHandler.getCorsHeaders() +
				"\r\n";
	}

	public String getContent() {
		return content;
	}

	public String getResponse() {
		if (content == null || content.isEmpty()) {
			Logger.error("Response content is null or empty");
			return NOT_FOUND;
		}

		return getEmptyResponse() + content;
	}

	public String getContentType() {
		return contentType;
	}

	protected String getContentLengthHeader() {
		return "Content-Length: " + getContentLength() + "\r\n";
	}

	public void setRequestHandler(RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}
}
