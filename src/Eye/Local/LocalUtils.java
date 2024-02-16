package Eye.Local;

import Eye.Logger.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocalUtils {
	public static byte[] GetBinaryFileContent(String path) throws IOException {
		try {
			Path filePath = Path.of(path);
			return Files.readAllBytes(filePath);
		} catch (IOException e) {
			Logger.error(e.getMessage());
			throw e;
		}
	}

	public static String GetFileContent(String path) throws IOException {
		return new String(GetBinaryFileContent(path));
	}
}
