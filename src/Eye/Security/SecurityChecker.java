package Eye.Security;

import Eye.RequestHandler;
import Eye.Server;
import Eye.Logger.Logger;

import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class SecurityChecker {
	public static boolean isSecure(RequestHandler requestHandler, Path absPath) {
		if (absPath == null) return requestHandler.isAuthorized();
		return SecurityChecker.pathIsOk(absPath.toString()) && requestHandler.isAuthorized();
	}

	private static boolean pathIsOk(String path) {
		try {
			Path evaluatedPath = Paths.get(path).toRealPath(LinkOption.NOFOLLOW_LINKS);
			return evaluatedPath.toString().contains(Server.getRootPath().toString());
		} catch (Exception e) {
			Logger.warning("Path not secure: " + path + "\n" + e.getMessage());
			return false;
		}
	}
}
