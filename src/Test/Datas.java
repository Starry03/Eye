package Test;

import Webserver.Response.JSON;
import Webserver.Route;

public class Datas extends Route {
	public Datas() {
		super("/datas");
	}

	@Override
	public String response() {
		JSON response = new JSON(GetFileContent("src/datas.json"));
		return response.getResponse();
	}
}
