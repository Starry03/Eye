package Eye.Response;

import Eye.RequestHandler;

public final class FILE extends Response {
	private final byte[] bytes;

	public FILE(byte[] content, String contentType) {
		super("content is bytes", contentType);
		this.bytes = content;
		contentLength = bytes.length;
	}

	@Override
	public String getResponse() {
		return "";
	}

	public byte[] getByteResponse() {
		String headers = Response.OK +
				getContentType() +
				getContentLengthHeader() +
				requestHandler.getCorsHeaders() +
				"\r\n";
		byte[] head = headers.getBytes();
		byte[] response = new byte[head.length + bytes.length];

		System.arraycopy(head, 0, response, 0, head.length);
		System.arraycopy(bytes, 0, response, head.length, bytes.length);

		return response;
	}
}
