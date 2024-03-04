package Eye.Response;

import java.nio.file.Path;

public final class HTML extends Response {
	public HTML(String content) {
		super(content, "Content-Type: text/html\r\n");
	}

	public HTML(Path path) {
		super(path, "Content-Type: text/html\r\n");
	}
}
