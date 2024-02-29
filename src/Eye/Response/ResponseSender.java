package Eye.Response;

import Local.FileManager;
import Logger.Logger;
import Eye.RequestHandler;
import Eye.Route.Route;
import Eye.Route.RoutesHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public abstract class ResponseSender {
	public static void send(String path, OutputStream outputStream, RoutesHandler routesHandler, RequestHandler requestHandler) {
		if (!requestHandler.authorized()) {
			Logger.warning("Unauthorized request");
			writeResponse(Response.UNAUTHORIZED, outputStream);
			return;
		}

		Route route = routesHandler.getRoutes().get(path);
		if (route != null)
			sendRouteResponse(route, outputStream, requestHandler);
		else
			sendFileResponse(outputStream, requestHandler);
	}

	private static void sendRouteResponse(Route route, OutputStream outputStream, RequestHandler requestHandler) {
		route.setRequestHandler(requestHandler);
		try {
			String response = route.getResponse();
			response = ResponseSender.insertCorsHeaders(response, requestHandler);
			writeResponse(response, outputStream);
			if (response.startsWith(Response.OK))
				Logger.info("Response sent: " + route.getPath());
			else Logger.warning("Path: " + route.getPath() + "\n" + "Response: unauthorized or server error");
		} catch (IOException e) {
			Logger.error(e.getMessage());
			writeResponse(Response.SERVER_ERROR, outputStream);
		}
	}

	private static void sendFileResponse(OutputStream outputStream, RequestHandler requestHandler) {
		Logger.warning("Route not found");
		try {
			byte[] content = FileManager.GetBinaryFileContent(requestHandler.getPath());
			FILE f = new FILE(content, "file/unknown");
			writeResponse(f.getByteResponse(requestHandler), outputStream);
			Logger.info("Response sent: " + requestHandler.getPath());
		} catch (IOException e) {
			writeResponse(Response.NOT_FOUND, outputStream);
			Logger.error(e.getMessage());
			Logger.error("File not found: 404 sent");
		}
	}

	private static void writeResponse(byte[] response, OutputStream outputStream) {
		try {
			outputStream.write(response);
			outputStream.flush();
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}
	}

	private static void writeResponse(String response, OutputStream outputStream) {
		writeResponse(response.getBytes(StandardCharsets.UTF_8), outputStream);
	}

	private static String insertCorsHeaders(String response, RequestHandler requestHandler) {
		// find first empty line
		int emptyLineIndex = response.indexOf("\r\n\r\n");
		if (emptyLineIndex == -1) {
			Logger.error("Response has no empty line");
			return response;
		}
		String sub = response.substring(0, emptyLineIndex);
		return sub + "\r\n" +
				requestHandler.getCorsHeaders() +
				response.substring(emptyLineIndex);
	}
}
