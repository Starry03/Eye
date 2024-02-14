package StarryServerConfig;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.Scanner;

public class RequestHandler {
	private final Scanner scanner;
	private String path;
	private String method;
	private String protocol;
	private String host;
	private String userAgent;
	private String accept;
	private String acceptLanguage;
	private String acceptEncoding;
	private String connection;
	private String cookie;
	private final LocalTime time;

	public RequestHandler(InputStream inputStream) {
		this.scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
		this.path = "";
		this.method = "";
		this.protocol = "";
		this.host = "";
		this.userAgent = "";
		this.accept = "";
		this.acceptLanguage = "";
		this.acceptEncoding = "";
		this.connection = "";
		this.cookie = "";
		this.time = LocalTime.now();
		this.parseRequest();
	}

	private void parseRequest() {
		String line = scanner.nextLine();
		String[] requestLine = line.split(" ");
		this.method = requestLine[0];
		this.path = requestLine[1];
		this.protocol = requestLine[2];
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.isEmpty()) {
				break;
			}
			String[] header = line.split(": ");
			switch (header[0]) {
				case "Host":
					this.host = header[1];
					break;
				case "User-Agent":
					this.userAgent = header[1];
					break;
				case "Accept":
					this.accept = header[1];
					break;
				case "Accept-Language":
					this.acceptLanguage = header[1];
					break;
				case "Accept-Encoding":
					this.acceptEncoding = header[1];
					break;
				case "Connection":
					this.connection = header[1];
					break;
				case "Cookie":
					this.cookie = header[1];
					break;
			}
		}
	}

	public String getPath() {
		return path;
	}

	public String getMethod() {
		return method;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getHost() {
		return host;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public String getAccept() {
		return accept;
	}

	public String getAcceptLanguage() {
		return acceptLanguage;
	}

	public String getAcceptEncoding() {
		return acceptEncoding;
	}

	public String getConnection() {
		return connection;
	}

	public String getCookie() {
		return cookie;
	}

	public LocalTime getTime() {
		return time;
	}

	@Override
	public String toString() {
		return "StarryServerConfig.RequestHandler{" +
				"scanner=" + scanner +
				", path='" + path + '\'' +
				", method='" + method + '\'' +
				", protocol='" + protocol + '\'' +
				", host='" + host + '\'' +
				", userAgent='" + userAgent + '\'' +
				", accept='" + accept + '\'' +
				", acceptLanguage='" + acceptLanguage + '\'' +
				", acceptEncoding='" + acceptEncoding + '\'' +
				", connection='" + connection + '\'' +
				", cookie='" + cookie + '\'' +
				", time=" + time +
				'}';
	}
}
