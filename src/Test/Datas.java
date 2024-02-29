package Test;

import Eye.RequestHandler;
import Local.FileManager;
import Eye.Response.JSON;
import Eye.Route.ProtectedRoute;

import java.io.IOException;

public class Datas extends ProtectedRoute {
	public Datas() {
		super("/datas");
	}

	@Override
	protected boolean isAuthorized() {
		RequestHandler requestHandler = getRequestHandler();
		String passcode = requestHandler.getQueryParams().get("passcode");
		if (passcode == null) return false;
		return passcode.equals("passcode");
	}

	@Override
	protected String response() throws IOException {
		JSON response = new JSON(FileManager.GetFileContent("datas.json"));
		return response.getResponse();
	}
}
