package jpmc.team12.handson.db;

public class Credentials {

	private static boolean isLoggedIn = false;

	private static String username;

	private static int uid;

	public static boolean getLoggedIn() {
		return Credentials.isLoggedIn;
	}

	public static String getUsername() {
		return Credentials.username;
	}

	public static int getUid() {
		return Credentials.uid;
	}

	public static void logIn(String username) {
		Credentials.isLoggedIn = true;
		Credentials.username = username;
	}

}
