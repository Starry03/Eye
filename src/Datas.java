import Webserver.Route;
import Webserver.Response;

public class Datas extends Route {
	public Datas() {
		super("/datas");
		setPath("/datas");
	}

	@Override
	public String response() {
		return Response.JSON(GetFileContent("src/datas.json"));
	}
}
