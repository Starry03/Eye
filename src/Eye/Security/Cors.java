package Eye.Security;

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

	private boolean isOriginAllowed(String origin) {
		return true;
	}

	private boolean isMethodAllowed(String method) {
		return true;
	}

	private boolean isFetchModeAllowed(String fetchMode) {
		return true;
	}

	public boolean isAllowed(String fetchMode, String fetchSite, String fetchUser) {

		return true;
	}

	public String getOriginHeader(String fetchSite) {
		StringBuilder res = new StringBuilder("Access-Control-Allow-Origin: ");
		if (fetchSite == null) return res.append("*\r\n").toString();
		if (fetchSite.equals("same-origin") || fetchSite.equals("none"))
			return res.append("*\r\n").toString();
		for (String allowedOrigin : allowedOrigins)
			if (allowedOrigin.equals(fetchSite))
				return res.append(fetchSite).append("\r\n").toString();
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
