package Eye.Response;

import Eye.RequestHandler;

public final class FILE extends Response {
	private final byte[] bytes;

	public FILE(byte[] content, String contentType) {
		super("content is bytes", contentType);
		this.bytes = content;
	}

	@Override
	public String getResponse() {
		return "";
	}

	public byte[] getByteResponse(RequestHandler requestHandler) {
		String emptyResponse = getEmptyResponse();
		String corsHeaders = requestHandler.getCorsHeaders();
		int emptyLineIndex = emptyResponse.indexOf("\r\n\r\n");
		String sub = emptyResponse.substring(0, emptyLineIndex);
		String res = sub + "\r\n" + corsHeaders + emptyResponse.substring(emptyLineIndex);
		String response = res + new String(bytes);
		return response.getBytes();
	}
}
