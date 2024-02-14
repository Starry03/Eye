package Webserver.Log;

public class Logger {
	private Logger() {}
	private static String defaultColor = Color.WHITE;

	public static void setDefaultColor(String color) {
		defaultColor = color;
	}
	private static void setDefault() {
		System.out.println(defaultColor);
	}
	private static void setBlue() {
		System.out.println(Color.BLUE);
	}
	public static void info(String message) {
		setBlue();
		System.out.println("--- INFO ---");
		System.out.println(message);
		setDefault();
	}
}
