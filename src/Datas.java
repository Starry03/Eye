import StarryServerConfig.Route;
import StarryServerConfig.Response;

public class Datas extends Route {
	public Datas() {
		setPath("/datas");
	}

	@Override
	public String response() {
		return Response.JSON(GetFileContent("src/datas.json"));
	}
}
