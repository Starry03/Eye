package Eye.Security;

import Eye.Logger.Logger;
import Eye.RequestHandler;
import Eye.Server;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class SecurityChecker {
	public static boolean isSecure(Path absPath) {
		return SecurityChecker.pathIsOk(absPath.toString());
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
