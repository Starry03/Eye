package Eye.Local;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {

	public static byte[] GetBinaryFileContent(String path) throws IOException {
		return GetBinaryFileContent(Path.of(path));
	}
	public static byte[] GetBinaryFileContent(Path path) throws IOException {
		Path filePath = Paths.get(path.toString());
		return Files.readAllBytes(filePath);
	}
	public static String GetFileContent(String path) throws IOException {
		return GetFileContent(Path.of(path));
	}

	public static String GetFileContent(Path path) throws IOException {
		return new String(GetBinaryFileContent(path));
	}
}
