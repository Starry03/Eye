package Eye.Response;

import Eye.Logger.Logger;
import Eye.RequestHandler;
import Eye.Route.Route;
import Eye.Route.RoutesHandler;
import Eye.Security.SecurityChecker;
import Eye.Server;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 1. chooses between sending route response or file response
 * 2. verifies if request is authorized
 * 3. verifies if security is ok
 * 4. sends response to client
 */
public abstract class ResponseSender {

	/**
	 * Chooses between sending route response or file response
	 *
	 * @param path           path to be sent
	 * @param outputStream   output stream
	 * @param routesHandler  routes handler
	 * @param requestHandler request handler
	 */
	public static void send(String path, OutputStream outputStream,
	                        RoutesHandler routesHandler, RequestHandler requestHandler) {
		Route route = routesHandler.getRoutes().get(path);
		if (route != null)
			sendRouteResponse(route, outputStream, requestHandler);
		else
			sendFileResponse(outputStream, requestHandler, null);
	}

	/**
	 * Sends response to client
	 *
	 * @param route          route to be sent
	 * @param outputStream   output stream
	 * @param requestHandler request handler
	 */
	private static void sendRouteResponse(Route route, OutputStream outputStream, RequestHandler requestHandler) {
		route.setRequestHandler(requestHandler);
		try {
			if (!SecurityChecker.isSecure(requestHandler, null)) {
				writeResponse(Response.SERVER_ERROR, -2, outputStream);
				Logger.warning("Path: " + route.getPath() + "\nResponse: Server error");
				return;
			}
			Response response = route.getResponse();
			if (response.getPath() != null) {
				sendFileResponse(outputStream, requestHandler, route.getResponse().getPath());
				return;
			}
			response.setRequestHandler(requestHandler);
			writeResponse(response.getResponse(), -2, outputStream);
		} catch (IOException e) {
			writeResponse(Response.SERVER_ERROR, -2, outputStream);
			Logger.error(requestHandler.toString() + "\nResponse: server error");
		}
	}

	/**
	 * Sends response to client
	 *
	 * @param outputStream   output stream
	 * @param requestHandler request handler
	 */
	private static void sendFileResponse(OutputStream outputStream, RequestHandler requestHandler, Path relPath) {
		Path absPath;

		try {
			if (relPath != null)
				absPath = Paths.get(Server.getRootPath().toString(), relPath.toString());
			else
				absPath = Paths.get(Server.getRootPath().toString(), requestHandler.getPath());
		} catch (Exception e) {
			writeResponse(Response.BAD_REQUEST, -2, outputStream);
			Logger.warning
					(requestHandler.toString() + "\nResponse: bad request");
			return;
		}
		try {
			if (!SecurityChecker.isSecure(requestHandler, absPath)) {
				writeResponse(Response.FORBIDDEN, -2, outputStream);
				Logger.warning(requestHandler.toString() + "\nResponse: forbidden");
				return;
			}
			ByteStreamResponse res = new ByteStreamResponse(absPath.toString(), requestHandler);
			res.streamBytes(outputStream);
		} catch (IOException e) {
			writeResponse(Response.SERVER_ERROR, -2, outputStream);
			Logger.error(requestHandler.toString() + "\nResponse: server error");
		}
	}

	/**
	 * Writes response to output stream and flushes it
	 *
	 * @param response     response to be written
	 * @param outputStream output stream
	 */
	public static void writeResponse(byte[] response, int len, OutputStream outputStream) {
		byte[] buf;

		if (len == -1) return;
		if (len < response.length && len != -2) {
			buf = new byte[len];
			System.arraycopy(response, 0, buf, 0, len);
		} else buf = response;
		try {
			outputStream.write(buf);
			outputStream.flush();
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}
	}

	/**
	 * Writes response to output stream and flushes it
	 *
	 * @param response     response to be written
	 * @param outputStream output stream
	 */
	public static void writeResponse(String response, int len, OutputStream outputStream) {
		writeResponse(response.getBytes(StandardCharsets.UTF_8), len, outputStream);
	}
}
