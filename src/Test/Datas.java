package Test;

import Local.FileManager;
import Eye.Response.JSON;
import Eye.Route.ProtectedRoute;

import java.io.IOException;

public class Datas extends ProtectedRoute {
	public Datas() {
		super("/datas");
	}

	@Override
	protected String response() throws IOException {
		JSON response = new JSON(FileManager.GetFileContent("datas.json"));
		return response.getResponse();
	}
}
