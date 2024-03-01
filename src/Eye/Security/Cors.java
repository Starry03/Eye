package Eye.Security;

import Eye.RequestHandler;
import Logger.Logger;

public class Cors {
	private final String[] allowedOrigins;
	private final String[] allowedMethods;
	private final String[] allowedHeaders;
	private static final String ALL_WILDCARD = "*";

	/**
	 * unsafe CORS constructor
	 */
	public Cors() {
		this.allowedOrigins = new String[]{ALL_WILDCARD};
		this.allowedMethods = new String[]{"GET", "POST", "PUT", "DELETE", "OPTIONS"};
		this.allowedHeaders = new String[]{ALL_WILDCARD};
	}

	public Cors(String[] allowedOrigins, String[] allowedMethods, String[] allowedHeaders) {
		this.allowedOrigins = allowedOrigins;
		this.allowedMethods = allowedMethods;
		this.allowedHeaders = allowedHeaders;
	}

	public String[] getAllowedOrigins() {
		return allowedOrigins;
	}

	public String[] getAllowedMethods() {
		return allowedMethods;
	}

	public String[] getAllowedHeaders() {
		return allowedHeaders;
	}

	private boolean arrContains(String[] array, String value) {
		for (String s : array)
			if (s.equals(value))
				return true;
		return false;
	}

	private boolean isOriginAllowed(String origin) {
		if (this.allowedOrigins[0].equals(ALL_WILDCARD)) return true;
		if (origin == null) return true;
		return arrContains(allowedOrigins, origin);
	}

	private boolean isMethodAllowed(String method) {
		if (this.allowedMethods[0].equals(ALL_WILDCARD)) return true;
		return arrContains(allowedMethods, method);
	}

	private boolean isHeadersAllowed(RequestHandler requestHandler) {
		if (this.allowedHeaders[0].equals(ALL_WILDCARD)) return true;
		String[] headers = requestHandler.getHeadersName();
		for (String header : headers)
			if (!arrContains(allowedHeaders, header))
				return false;
		return true;
	}

	public boolean isAllowed(RequestHandler requestHandler) {
		boolean isOriginAllowed = isOriginAllowed(requestHandler.getReferer());
		boolean isMethodAllowed = isMethodAllowed(requestHandler.getMethod());
		boolean isHeadersAllowed = isHeadersAllowed(requestHandler);
		boolean allowed = isOriginAllowed && isMethodAllowed && isHeadersAllowed;
		if (!allowed) {
			Logger.error(
					"Cors error\n" +
							"originAllowed: " + isOriginAllowed + "\n" +
							"methodAllowed: " + isMethodAllowed + "\n" +
							"headersAllowed: " + isHeadersAllowed
			);
		}
		return allowed;
	}

	public String getOriginHeader(String origin) {
		StringBuilder res = new StringBuilder("Access-Control-Allow-Origin: ");
		if (origin == null) return res.append("*\r\n").toString();
		if (origin.equals("same-origin") || origin.equals("none"))
			return res.append("*\r\n").toString();
		for (String allowedOrigin : allowedOrigins)
			if (allowedOrigin.equals(origin))
				return res.append(origin).append("\r\n").toString();
		return res.append("none\r\n").toString();
	}

	public String getAllowedMethodsHeader() {
		StringBuilder res = new StringBuilder("Access-Control-Allow-Methods: ");
		for (String method : allowedMethods)
			res.append(method).append(", ");
		return res.substring(0, res.length() - 2) + "\r\n";
	}

	public String getAllowedHeadersHeader() {
		StringBuilder res = new StringBuilder("Access-Control-Allow-Headers: ");
		for (String header : allowedHeaders)
			res.append(header).append(", ");
		return res.substring(0, res.length() - 2) + "\r\n";
	}

}
