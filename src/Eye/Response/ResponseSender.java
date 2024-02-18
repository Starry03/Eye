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
		Route route = routesHandler.getRoutes().get(path);
		if (route != null)
			sendRouteResponse(route, outputStream, requestHandler);
		else
			sendFileResponse(path, outputStream);
	}

	private static void sendRouteResponse(Route route, OutputStream outputStream, RequestHandler requestHandler) {
		route.setRequestHandler(requestHandler);
		try {
			writeResponse(route.getResponse(), outputStream);
			Logger.info("Response sent: " + route.getPath());
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}
	}

	private static void sendFileResponse(String path, OutputStream outputStream) {
		Logger.warning("Route not found");
		try {
			byte[] content = FileManager.GetBinaryFileContent(path);
			FILE f = new FILE(content, "file/unknown");
			writeResponse(f.getByteResponse(), outputStream);
			Logger.info("Response sent: " + path);
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
}
