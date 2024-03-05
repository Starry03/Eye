package Eye.Logger;

public abstract class Logger {
	/**
	 * Logger class for logging messages to the console.
	 * It can log messages with different colors.
	 * It can be turned off.
	 */
	private static boolean isActive = true;
	private static String defaultColor = Color.WHITE;

	public static void setDefaultColor(String color) {
		defaultColor = color;
	}

	private static void setDefault() {
		System.out.print(defaultColor);
	}

	/**
	 * Sets the logger active or inactive.
	 *
	 * @param active true if active, false if inactive
	 */
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

	public static void debug(String message) {
		log(message, Color.BLUE, "--- DEBUG ---");
	}

	private static void log(String message, String color, String label) {
		if (!isActive) return;
		System.out.print(color);
		System.out.println(label);
		setDefault();
		System.out.println(message);
	}
}
