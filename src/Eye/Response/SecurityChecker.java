package Eye.Response;

import Eye.RequestHandler;
import Eye.Server;
import Logger.Logger;

import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public abstract class SecurityChecker {
	public static boolean isSecure(RequestHandler requestHandler, Path relativePath) {
		if (relativePath == null) return requestHandler.isAuthorized();
		return SecurityChecker.pathIsOk(
				relativePath.toString()
		) && requestHandler.isAuthorized();
	}

	private static boolean pathIsOk(String path) {
		try {
			Path evaluatedPath = Paths.get(path).toRealPath(LinkOption.NOFOLLOW_LINKS);
			Logger.info("Evaluated path: " + evaluatedPath);
			return evaluatedPath.toString().contains(Server.getRootPath().toString());
		} catch (Exception e) {
			System.out.println("Path: " + path);
			return false;
		}
	}
}
