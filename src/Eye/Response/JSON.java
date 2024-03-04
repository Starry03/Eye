package Eye.Response;

import java.nio.file.Path;

public final class JSON extends Response {
	public JSON(String content) {
		super(content, "Content-Type: application/json\r\n");
	}

	public JSON(Path path) {
		super(path, "Content-Type: application/json\r\n");
	}
}
