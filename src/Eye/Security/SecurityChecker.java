package Eye.Security;

import Eye.RequestHandler;
import Eye.Server;
import Eye.Logger.Logger;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class SecurityChecker {
	public static boolean isSecure(RequestHandler requestHandler, Path absPath) {
		if (absPath == null) return requestHandler.isAuthorized();
		return SecurityChecker.pathIsOk(absPath.toString()) && requestHandler.isAuthorized();
	}

	/**
	 * Verifies if path is not a security risk
	 * @param path
	 * @return
	 */
	private static boolean pathIsOk(String path) {
		try {
			Path evaluatedPath = Paths.get(path).toRealPath(LinkOption.NOFOLLOW_LINKS);
			return evaluatedPath.toString().contains(Server.getRootPath().toString());
		} catch (InvalidPathException e) {
			Logger.warning("Invalid path: " + path + "\n" + e.getMessage());
			return false;
		}
		catch (IOException e) {
			Logger.error(e.getMessage());
			return false;
		}
	}
}
