package Eye.Response;

import Eye.Logger.Logger;
import Eye.RequestHandler;

import java.nio.file.Path;

public abstract class Response {
	private Path path;
	private String content;
	private final String contentType;
	private RequestHandler requestHandler;

	protected long contentLength;

	protected Response(String content, String contentType) {
		this.content = content;
		this.contentType = contentType;
	}

	protected Response(Path path) {
		this.path = path;
		this.contentType = "application/octet-stream";
	}

	protected Response(Path path, String contentType) {
		this.path = path;
		this.contentType = contentType;
	}

	public Path getPath() {
		return path;
	}

	protected final void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	private long getContentLength() {
		return contentLength;
	}

	/**
	 * @return positive response with minimal headers
	 */
	protected final String getEmptyResponse() {
		return Responses.OK +
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
			return Responses.NOT_FOUND;
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
	public RequestHandler getRequestHandler() { return requestHandler; }
}
