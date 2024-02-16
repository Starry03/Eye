package Test;

import Eye.Local.LocalUtils;
import Eye.Response.JSON;
import Eye.Route;

import java.io.IOException;

public class Datas extends Route {
	public Datas() {
		super("/datas");
	}

	@Override
	public String response() throws IOException {
		JSON response = new JSON(LocalUtils.GetFileContent("src/datas.json"));
		return response.getResponse();
	}
}
