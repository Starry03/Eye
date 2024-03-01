package Eye;

import Eye.Security.Cors;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Scanner;

public class RequestHandler {
	HashMap<String, String> headers = new HashMap<>();
	private final LocalTime time;
	private final HashMap<String, String> queryParams;
	private final Cors cors;
	private final boolean authorized;

	public RequestHandler(Scanner scanner, Server server) {
		this.time = LocalTime.now();
		this.queryParams = new HashMap<>();
		this.parseRequest(scanner);
		this.cors = server.getCors();
		this.authorized = cors.isAllowed(this);
	}

	public String getCorsHeaders() {
		return cors.getOriginHeader(getHeader("referer")) +
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

	public LocalTime getTime() {
		return time;
	}

	public String[] getHeadersName() {
		return headers.keySet().toArray(new String[0]);
	}

	private void parseRequest(Scanner scanner) {
		if (!scanner.hasNextLine()) return;
		String line = scanner.nextLine();
		String[] requestLine = line.split(" ");
		headers.put("method", requestLine[0]);
		if (requestLine[1].contains("?")) {
			String[] res = requestLine[1].split("\\?");
			headers.put("path", res[0]);
			this.buildQueryParams(res[1].split("&"));
		} else headers.put("path", requestLine[1]);
		headers.put("protocol", requestLine[2]);
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.isEmpty())
				break;
			String[] header = line.split(": ");
			headers.put(header[0].toLowerCase(), header[1]);
		}
	}

	public String getPath() {
		return headers.get("path");
	}

	public String getMethod() {
		return headers.get("method");
	}

	public String getHeader(String header) {
		return headers.get(header);
	}

	public HashMap<String, String> getQueryParams() {
		return queryParams;
	}

	public boolean isAuthorized() {
		return authorized;
	}

	public String getReferer() {
		return headers.get("Referer");
	}
}
