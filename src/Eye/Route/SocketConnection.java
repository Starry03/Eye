package Eye.Route;

import Eye.Response.HTML;
import Eye.Response.Response;
import Eye.Logger.Logger;
import Eye.Response.ResponseSender;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class SocketConnection extends ProtectedRoute {
	private final Socket socket;

	public SocketConnection(String path) {
		super(path);
		socket = getRequestHandler().getSocket();
		try {
			socket.setKeepAlive(true);
		} catch (SocketException e) {
			Logger.error(e.getMessage());
		}

	}

	@Override
	public Response response() {
		try {
			socket.setKeepAlive(false);
		} catch (SocketException e) {
			Logger.error(e.getMessage());
		}
		return new HTML("Default response");
	}

	public void send(String response) throws IOException {
		ResponseSender.writeResponse(response, response.length(), socket.getOutputStream());
	}

	public void send(byte[] response) throws IOException {
		int length = response.length;
		ResponseSender.writeResponse(response, length, socket.getOutputStream());
	}
}
