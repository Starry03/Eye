package Eye.Response;

import Eye.Security.SecurityChecker;
import Eye.Server;
import Eye.Local.FileManager;
import Eye.Logger.Logger;
import Eye.RequestHandler;
import Eye.Route.Route;
import Eye.Route.RoutesHandler;

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
	 * @param path            path to be sent
	 * @param outputStream    output stream
	 * @param routesHandler   routes handler
	 * @param requestHandler  request handler
	*/
	public static void send(String path, OutputStream outputStream,
	                        RoutesHandler routesHandler, RequestHandler requestHandler) {
		Route route = routesHandler.getRoutes().get(path);
		if (route != null)
			sendRouteResponse(route, outputStream, requestHandler);
		else
			sendFileResponse(outputStream, requestHandler);
	}

	/**
	 * Sends response to client
	 *
	 * @param route           route to be sent
	 * @param outputStream    output stream
	 * @param requestHandler  request handler
	*/
	private static void sendRouteResponse(Route route, OutputStream outputStream, RequestHandler requestHandler) {
		route.setRequestHandler(requestHandler);
		try {
			if (!SecurityChecker.isSecure(requestHandler, null)) {
				writeResponse(Response.SERVER_ERROR, outputStream);
				Logger.warning("Path: " + route.getPath() + "\n" + "Response: Server error");
				return;
			}
			Logger.info("Sending response from route: " + route.getPath());
			Response response = route.getResponse();
			response.requestHandler = requestHandler;
			writeResponse(response.getResponse(), outputStream);
			Logger.info("Response sent: " + route.getPath());
		} catch (IOException e) {
			Logger.error(e.getMessage());
			writeResponse(Response.SERVER_ERROR, outputStream);
		}
	}

	/**
	 * Sends response to client
	 *
	 * @param outputStream    output stream
	 * @param requestHandler  request handler
	*/
	private static void sendFileResponse(OutputStream outputStream, RequestHandler requestHandler) {
		Path absPath;
		try {
			absPath = Paths.get(Server.getRootPath().toString(), requestHandler.getPath());
		}
		catch (Exception e) {
			writeResponse(Response.BAD_REQUEST, outputStream);
			Logger.warning("Path: " + requestHandler.getPath() + "\n" + "Response: bad request");
			return;
		}
		try {
			if (!SecurityChecker.isSecure(requestHandler, absPath)) {
				writeResponse(Response.FORBIDDEN, outputStream);
				Logger.warning("Path: " + requestHandler.getPath() + "\n" + "Response: forbidden");
				return;
			}
			byte[] content = FileManager.GetBinaryFileContent(absPath);
			FILE file = new FILE(content, "file/unknown");
			file.requestHandler = requestHandler;
			byte[] response = file.getByteResponse();
			writeResponse(response, outputStream);
			Logger.info("Response sent: " + requestHandler.getPath());
		} catch (IOException e) {
			writeResponse(Response.NOT_FOUND, outputStream);
			Logger.error(e.getMessage());
			Logger.error("File not found: 404 sent");
		}
	}

	/**
	 * Writes response to output stream and flushes it
	 *
	 * @param response      response to be written
	 * @param outputStream  output stream
	*/
	private static void writeResponse(byte[] response, OutputStream outputStream) {
		try {
			outputStream.write(response);
			outputStream.flush();
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}
	}

	/**
	 * Writes response to output stream and flushes it
	 *
	 * @param response      response to be written
	 * @param outputStream  output stream
	*/
	private static void writeResponse(String response, OutputStream outputStream) {
		writeResponse(response.getBytes(StandardCharsets.UTF_8), outputStream);
	}

	/**
	 * Inserts CORS headers into response
	 *
	 * @param response         response to be modified
	 * @param requestHandler   request handler
	 * @return                 response with CORS headers
	*/
	private static String insertCorsHeaders(String response, RequestHandler requestHandler) {
		int emptyLineIndex = response.indexOf("\r\n\r\n");
		if (emptyLineIndex == -1) {
			Logger.error("Response has no empty line");
			return response;
		}
		String sub = response.substring(0, emptyLineIndex);
		return sub + "\r\n" +
				requestHandler.getCorsHeaders() +
				response.substring(emptyLineIndex + 2);
	}
}
