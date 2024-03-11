package Eye.Security;

import Eye.Logger.Logger;
import Eye.RequestHandler;

import java.util.Arrays;

public class Cors {
	/**
	 * @brief cors policy class
	 */
	private final String[] allowedOrigins;
	private final String[] allowedMethods;
	private final String[] allowedHeaders;
	private static final String ALL_WILDCARD = "*";

	/**
	 * Unsafe default cors constructor
	 */
	public Cors() {
		this.allowedOrigins = new String[]{ALL_WILDCARD};
		this.allowedMethods = new String[]{"GET", "POST", "PUT", "DELETE", "OPTIONS"};
		this.allowedHeaders = new String[]{ALL_WILDCARD};
	}

	/**
	 * Safe cors constructor
	 *
	 * @param allowedOrigins the allowed origins
	 * @param allowedMethods the allowed methods
	 * @param allowedHeaders the allowed headers
	 */
	public Cors(String[] allowedOrigins, String[] allowedMethods, String[] allowedHeaders) {
		this.allowedOrigins = allowedOrigins;
		this.allowedMethods = allowedMethods;
		this.allowedHeaders = allowedHeaders;
	}

	/**
	 * @return the allowed origins
	 */
	public String[] getAllowedOrigins() {
		return allowedOrigins;
	}

	/**
	 * @return the allowed methods
	 */
	public String[] getAllowedMethods() {
		return allowedMethods;
	}

	/**
	 * @return the allowed headers
	 */
	public String[] getAllowedHeaders() {
		return allowedHeaders;
	}

	private static boolean arrContains(String[] array, String value) {
		return Arrays.asList(array).contains(value);
	}

	/**
	 * @param origin the origin
	 * @return bool
	 * @brief checks if the origin is allowed by the cors policy implemented by the server
	 */
	private boolean isOriginAllowed(String origin) {
		if (allowedOrigins[0].equals(ALL_WILDCARD)) return true;
		if (origin == null) return true;
		return arrContains(allowedOrigins, origin);
	}

	/**
	 * @param method the method
	 * @return if the method is allowed by the cors policy
	 */
	private boolean isMethodAllowed(String method) {
		if (this.allowedMethods[0].equals(ALL_WILDCARD)) return true;
		return arrContains(allowedMethods, method);
	}

	/**
	 * @param requestHandler the request handler
	 * @return if the headers are allowed by the cors policy
	 */
	private boolean isHeadersAllowed(RequestHandler requestHandler) {
		if (this.allowedHeaders[0].equals(ALL_WILDCARD)) return true;
		String[] headers = requestHandler.getHeadersName();
		for (String header : headers)
			if (!arrContains(allowedHeaders, header))
				return false;
		return true;
	}

	/**
	 * @param origin the origin
	 * @return a string with the origin http header
	 */
	public String getOriginHeader(String origin) {
		StringBuilder res = new StringBuilder("Access-Control-Allow-Origin: ");
		if (origin == null || allowedOrigins[0].equals(ALL_WILDCARD)) return res.append("*\r\n").toString();
		if (origin.equals("same-origin") || origin.equals("none"))
			return res.append("*\r\n").toString();
		for (String allowedOrigin : allowedOrigins)
			if (allowedOrigin.equals(origin))
				return res.append(origin).append("\r\n").toString();
		Logger.error("Origin: blocked: " + origin);
		return "";
	}

	/**
	 * @return a string with the allowed methods http header
	 */
	public String getAllowedMethodsHeader() {
		StringBuilder res = new StringBuilder("Access-Control-Allow-Methods: ");
		for (String method : allowedMethods)
			res.append(method).append(", ");
		return res.substring(0, res.length() - 2) + "\r\n";
	}

	/**
	 * @return a string with the allowed headers http header
	 */
	public String getAllowedHeadersHeader() {
		StringBuilder res = new StringBuilder("Access-Control-Allow-Headers: ");
		for (String header : allowedHeaders)
			res.append(header).append(", ");
		return res.substring(0, res.length() - 2) + "\r\n";
	}

}
