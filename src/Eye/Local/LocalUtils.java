package Eye.Local;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocalUtils {
	public static byte[] GetBinaryFileContent(String path) throws IOException {
		Path filePath = Path.of(path);
		return Files.readAllBytes(filePath);
	}

	public static String GetFileContent(String path) throws IOException {
		return new String(GetBinaryFileContent(path));
	}
}
