package Eye.Response;

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

	public byte[] getByteResponse() {
		int i = 0;
		int totalLength = getEmptyResponse().length() + bytes.length;
		byte[] response = new byte[totalLength];
		for (byte b : getEmptyResponse().getBytes())
			response[i++] = b;
		for (byte b : bytes)
			response[i++] = b;
		return response;
	}
}
