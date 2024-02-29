package Eye;

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
	private String fetchMode;
	private String fetchSite;
	private String fetchUser;
	private final LocalTime time;
	private final HashMap<String, String> queryParams;
	private final boolean authorized;
	private final Cors cors;

	public RequestHandler(Scanner scanner, Cors cors) {
		this.time = LocalTime.now();
		this.queryParams = new HashMap<>();
		this.parseRequest(scanner);
		this.cors = cors;
		this.authorized = cors.isAllowed(
				fetchMode,
				fetchSite,
				fetchUser
		);
	}

	public String getCorsHeaders() {
		return cors.getOriginHeader(fetchSite) +
				cors.getAllowedMethodsHeader() +
				cors.getAllowedHeadersHeader() +
				"Access-Control-Allow-Credentials: true\r\n";
	}

	private void buildQueryParams(String[] params) {
		for (String parameter : params) {
			String[] values = parameter.split("=");
			queryParams.put(values[0], values[1]);
		}
	}

	private void parseRequest(Scanner scanner) {
		if (!scanner.hasNextLine()) return;
		String line = scanner.nextLine();
		String[] requestLine = line.split(" ");
		this.method = requestLine[0];
		if (requestLine[1].contains("?")) {
			String[] res = requestLine[1].split("\\?");
			this.path = res[0];
			this.buildQueryParams(res[1].split("&"));
		} else this.path = requestLine[1];
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
				case "Sec-Fetch-Site":
					this.fetchSite = header[1];
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

	public HashMap<String, String> getQueryParams() {
		return queryParams;
	}

	public String getFetchSite() {
		return fetchSite;
	}

	public String getFetchMode() {
		return fetchMode;
	}

	public String getFetchUser() {
		return fetchUser;
	}

	public boolean isAuthorized() {
		return authorized;
	}

	public boolean authorized() {
		return authorized;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("RequestHandler Details:\n");
		sb.append("Path: ").append(path).append("\n");
		sb.append("Method: ").append(method).append("\n");
		sb.append("Protocol: ").append(protocol).append("\n");
		sb.append("Host: ").append(host).append("\n");
		sb.append("User-Agent: ").append(userAgent).append("\n");
		sb.append("Accept: ").append(accept).append("\n");
		sb.append("Accept-Language: ").append(acceptLanguage).append("\n");
		sb.append("Accept-Encoding: ").append(acceptEncoding).append("\n");
		sb.append("Connection: ").append(connection).append("\n");
		sb.append("Cookie: ").append(cookie).append("\n");
		sb.append("Origin: ").append(fetchSite).append("\n");
		sb.append("Time: ").append(time).append("\n");
		sb.append("Query Parameters: ").append(queryParams).append("\n");
		return sb.toString();
	}
}
