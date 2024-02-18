package Test;

import Eye.Local.FileManager;
import Eye.ProtectedRoute;
import Eye.Response.JSON;

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
