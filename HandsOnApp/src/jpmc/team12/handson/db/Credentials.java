package jpmc.team12.handson.db;

public class Credentials {

	private static final String USERNAME_GUEST = "Guest";

	private static boolean isLoggedIn = false;

	private static String username = USERNAME_GUEST;

	private static String uid;

	public static boolean getLoggedIn() {
		return Credentials.isLoggedIn;
	}

	public static String getUsername() {
		return Credentials.username;
	}

	public static String getUid() {
		return Credentials.uid;
	}

	public static void logIn(String username, String user_id) {
		Credentials.isLoggedIn = true;
		Credentials.username = username;
		Credentials.uid = user_id;
	}

	public static void logOut() {
		Credentials.isLoggedIn = false;
		Credentials.username = USERNAME_GUEST;
	}

}
