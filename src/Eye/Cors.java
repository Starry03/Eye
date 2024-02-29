package Eye;

public class Cors {
	private final String[] allowedOrigins;
	private final String[] allowedMethods;
	private final String[] allowedHeaders;

	/**
	 * unsafe CORS constructor
	 */
	public Cors() {
		this.allowedOrigins = new String[]{"*"};
		this.allowedMethods = new String[]{"GET", "POST", "PUT", "DELETE", "OPTIONS"};
		this.allowedHeaders = new String[]{"*"};
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

	public boolean isAllowed(String fetchMode, String fetchSite, String fetchUser) {
		// TODO: implement CORS policy
		return true;
	}

	public String getOriginHeader(String origin) {
		StringBuilder res = new StringBuilder("Access-Control-Allow-Origin: ");
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
