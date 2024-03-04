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

	public ByteStreamResponse(String path) {
		super("content is bytes", "application/octet-stream");
		this.file = new File(path);
		setContentLength(file.length());
		setRequestHandler(requestHandler);
	}

	public ByteStreamResponse(String path, String contentType) {
		super("content is bytes", contentType);
		this.file = new File(path);
		setContentLength(file.length());
		setRequestHandler(requestHandler);
	}

	@Override
	public String getResponse() {
		return "";
	}

	private String getHeader() {
		return Response.OK +
				getContentType() +
				getContentLengthHeader() +
				requestHandler.getCorsHeaders() +
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
	 * @param requestHandler request handler
	 * @throws IOException if an I/O error occurs
	 */
	public void streamBytes(OutputStream stream, RequestHandler requestHandler) throws IOException {
		int bytesRead;
		int byteCount;
		final int bufferSize = 1024 * 64;
		byte[] buffer = new byte[bufferSize];
		RandomAccessFile randomAccessFile;

		setRequestHandler(requestHandler);
		try {
			randomAccessFile = new RandomAccessFile(file, "r");
		} catch (Exception e) {
			Logger.error(e.getMessage());
			return;
		}
		byteCount = 0;
		bytesRead = randomAccessFile.read(buffer, byteCount, bufferSize);
		byteCount += bytesRead;
		writeResponse(getInitialResponse(buffer), getHeader().length() + bytesRead, stream);
		while (bytesRead != -1) {
			bytesRead = randomAccessFile.read(buffer, 0, bufferSize);
			byteCount += bytesRead;
			writeResponse(buffer, bytesRead, stream);
		}
	}
}
