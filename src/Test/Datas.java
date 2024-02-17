package Test;

import Eye.Local.FileManager;
import Eye.Response.JSON;
import Eye.Route;

import java.io.IOException;

public class Datas extends Route {
	public Datas() {
		super("/datas");
	}

	@Override
	public String response() throws IOException {
		JSON response = new JSON(FileManager.GetFileContent("datas.json"));
		return response.getResponse();
	}
}
