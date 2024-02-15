package Webserver.Logger;

public class Logger {
	private Logger() {}
	private static String defaultColor = Color.WHITE;

	public static void setDefaultColor(String color) {
		defaultColor = color;
	}
	private static void setDefault() {
		System.out.println(defaultColor);
	}
	public static void setRed() {
		System.out.println(Color.RED);
	}
	public static void setGreen() {
		System.out.println(Color.GREEN);
	}
	private static void setBlue() {
		System.out.println(Color.BLUE);
	}
	public static void info(String message) {
		setGreen();
		System.out.println("--- INFO ---");
		setDefault();
		System.out.print(message);
	}

	public static void error(String message) {
		setRed();
		System.out.println("--- ERROR ---");
		setDefault();
		System.out.println(message);
	}
}
