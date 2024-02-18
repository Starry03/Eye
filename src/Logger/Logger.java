package Logger;

public abstract class Logger {
	private static boolean isActive = true;
	private static String defaultColor = Color.WHITE;
	public static void setDefaultColor(String color) {
		defaultColor = color;
	}

	private static void setDefault() {
		System.out.print(defaultColor);
	}

	public static void setActive(boolean active) {
		isActive = active;
	}

	public static boolean isActive() {
		return isActive;
	}

	public static void info(String message) {
		log(message, Color.GREEN, "--- INFO ---");
	}

	public static void error(String message) {
		log(message, Color.RED, "--- ERROR ---");
	}

	public static void warning(String message) {
		log(message, Color.ORANGE, "--- WARNING ---");
	}

	private static void log(String message, String color, String label) {
		if (!isActive) return;
		System.out.print(color);
		System.out.println(label);
		setDefault();
		System.out.println(message);
	}
}
