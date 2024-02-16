package Eye.Logger;

public class Logger {
	private Logger() {
	}

	private static String defaultColor = Color.WHITE;

	public static void setDefaultColor(String color) {
		defaultColor = color;
	}

	private static void setDefault() {
		System.out.print(defaultColor);
	}

	public static void setRed() {
		System.out.print(Color.RED);
	}

	public static void setGreen() {
		System.out.print(Color.GREEN);
	}

	private static void setBlue() {
		System.out.print(Color.BLUE);
	}

	public static void info(String message) {
		setGreen();
		System.out.println("--- INFO ---");
		setDefault();
		System.out.println(message);
	}

	public static void error(String message) {
		setRed();
		System.out.println("--- ERROR ---");
		setDefault();
		System.out.println(message);
	}

	public static void warning(String message) {
		setBlue();
		System.out.println("--- WARNING ---");
		setDefault();
		System.out.println(message);
	}
}
