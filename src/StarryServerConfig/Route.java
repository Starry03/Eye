package StarryServerConfig;

import java.io.IOException;
import java.io.File;
import java.io.FileReader;

@SuppressWarnings("SameParameterValue")
public abstract class Route {
	private String path;

	/**
	 * override this method to return a response
	 */
	public String response() {
		return "";
	}

	@Override
	public String toString() {
		return "RouterExecutable{" +
				"path='" + path + '\'' +
				'}';
	}

	protected String GetFileContent(String path) {
		try {
			File file = new File(path);
			FileReader fileReader = new FileReader(file);
			StringBuilder content = new StringBuilder();
			int i;
			while ((i = fileReader.read()) != -1) {
				content.append((char) i);
			}
			fileReader.close();
			return content.toString();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return "";
		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
