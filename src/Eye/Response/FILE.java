package Eye.Response;

public final class FILE extends Response {
	public FILE(byte[] content, String contentType) {
		super("content is bytes", contentType);
	}

	@Override
	public String getResponse() {
		return "";
	}
}
