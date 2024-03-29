package Eye.Response;

import Eye.Logger.Logger;
import Eye.RequestHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import static Eye.Response.ResponseSender.writeResponse;

public final class ByteStreamResponse extends Response {
	private final File file;

	/**
	 *
	 * @param path file path
	 * @param requestHandler request handler
	 */
	public ByteStreamResponse(String path, RequestHandler requestHandler) {
		super("content is bytes", "");
		this.file = new File(path);
		setContentLength(file.length());
		setRequestHandler(requestHandler);
	}

	/**
	 *
	 * @param path file path
	 * @param requestHandler request handler
	 * @param contentType content type header
	 */
	public ByteStreamResponse(String path, RequestHandler requestHandler, String contentType) {
		super("content is bytes", contentType);
		this.file = new File(path);
		setContentLength(file.length());
		setRequestHandler(requestHandler);
	}

	/**
	 * @return ""
	 */
	@Override
	public String getResponse() {
		return "";
	}

	/**
	 * @return headers
	 */
	private String getHeader() {
		return Responses.OK +
				getContentType() +
				getContentLengthHeader() +
				getRequestHandler().getCorsHeaders() +
				"\r\n";
	}

	/**
	 * @return positive response with minimal headers and initial bytes
	 */
	public byte[] getInitialResponse(byte[] bytes) {
		byte[] head = getHeader().getBytes();
		byte[] response = new byte[head.length + bytes.length];

		System.arraycopy(head, 0, response, 0, head.length);
		System.arraycopy(bytes, 0, response, head.length, bytes.length);

		return response;
	}

	/**
	 * Streams bytes to the client
	 *
	 * @param stream         output stream
	 * @throws IOException if an I/O error occurs
	 */
	public void streamBytes(OutputStream stream) throws IOException {
		int bytesRead;
		final int bufferSize = 1024 * 64;
		byte[] buffer = new byte[bufferSize];
		RandomAccessFile randomAccessFile;
		try {
			randomAccessFile = new RandomAccessFile(file, "r");
		} catch (Exception e) {
			Logger.error(e.getMessage());
			return;
		}
		bytesRead = randomAccessFile.read(buffer, 0, bufferSize);
		writeResponse(getInitialResponse(buffer), getHeader().length() + bytesRead, stream);
		while (bytesRead != -1) {
			bytesRead = randomAccessFile.read(buffer, 0, bufferSize);
			writeResponse(buffer, bytesRead, stream);
		}
		randomAccessFile.close();
	}
}
