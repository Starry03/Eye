package Test;

import Webserver.Local.LocalUtils;
import Webserver.Response.JSON;
import Webserver.Route;

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
