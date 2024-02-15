package Webserver;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Scanner;

public class RequestHandler {
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
	private final HashMap<String, String> queryParams;

	public RequestHandler(InputStream inputStream) {
		this.time = LocalTime.now();
		this.queryParams = new HashMap<>();
		this.parseRequest(inputStream);
	}

	private void buildQueryParams(String[] params) {
		for (String parameter: params) {
			String[] values = parameter.split("=");
			queryParams.put(values[0], values[1]);
		}
	}

	private void parseRequest(InputStream inputStream) {
		Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
		String line = scanner.nextLine();
		String[] requestLine = line.split(" ");
		this.method = requestLine[0];

		if (requestLine[1].contains("?")) {
			String[] res = requestLine[1].split("\\?");
			this.path = res[0];
			this.buildQueryParams(res[1].split("&"));
		}
		else this.path = requestLine[1];

		if (scanner.hasNextLine())
			this.protocol = requestLine[2];
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.isEmpty())
				break;
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
		scanner.close();
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

	public HashMap<String, String> getQueryParams() {
		return queryParams;
	}

	@Override
	public String toString() {
		return "RequestHandler{" +
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
