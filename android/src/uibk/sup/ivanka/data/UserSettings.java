/**
 * 
 */
package uibk.sup.ivanka.data;

import java.util.Map;

import android.content.SharedPreferences;

import com.google.android.gms.maps.GoogleMap;

/**
 * @author Daniel
 *
 */
public class UserSettings {

	private static SharedPreferences settings;
	private static SharedPreferences.Editor prefEditor;
	private static Map<String, ?> savedSharedPreferences;

	private static String username = "";
	private static String password = "";
	private static String smallMessage = "Enter your own message";
	private static String date = "";
	private static int searchRadius = 2000; // in meters
	private static int mapType = GoogleMap.MAP_TYPE_NORMAL;
	private static boolean autoLogin = true;
	private static boolean autoFill = false;

	private static final String USRNM = "privateUsername";
	private static final String PASSWD = "privatePassword";
	private static final String DATE = "privateDate";
	private static final String SMLLMSSG = "privateSmallMessage";
	private static final String SRCHRDS = "privateSearchRadius";
	private static final String MAPTYPE = "privateMapType";
	private static final String AUTOLOGIN = "privateAutoLogin";
	private static final String AUTOFILL = "privateAutoFill";

	public static void initUserSettings(SharedPreferences settings) {

		UserSettings.settings = settings;
		UserSettings.savedSharedPreferences = settings.getAll();

		if (!UserSettings.savedSharedPreferences.isEmpty()) {

			if (UserSettings.savedSharedPreferences.containsKey(USRNM))
				UserSettings.username = UserSettings.savedSharedPreferences
						.get(USRNM).toString();
			if (UserSettings.savedSharedPreferences.containsKey(PASSWD))
				UserSettings.password = UserSettings.savedSharedPreferences
						.get(PASSWD).toString();
			if (UserSettings.savedSharedPreferences.containsKey(DATE))
				UserSettings.date = UserSettings.savedSharedPreferences.get(
						DATE).toString();
			if (UserSettings.savedSharedPreferences.containsKey(SMLLMSSG))
				UserSettings.smallMessage = UserSettings.savedSharedPreferences
						.get(SMLLMSSG).toString();
			if (UserSettings.savedSharedPreferences.containsKey(SRCHRDS))
				UserSettings.searchRadius = (Integer) UserSettings.savedSharedPreferences
						.get(SRCHRDS);
			if (UserSettings.savedSharedPreferences.containsKey(MAPTYPE))
				UserSettings.mapType = (Integer) UserSettings.savedSharedPreferences
						.get(MAPTYPE);
			if (UserSettings.savedSharedPreferences.containsKey(AUTOLOGIN))
				UserSettings.autoLogin = (Boolean) UserSettings.savedSharedPreferences
						.get(AUTOLOGIN);
			if (UserSettings.savedSharedPreferences.containsKey(AUTOFILL))
				UserSettings.autoFill = (Boolean) UserSettings.savedSharedPreferences
						.get(AUTOFILL);
		}
	}

	public static boolean isBasicDataSet() {

		if (UserSettings.savedSharedPreferences.containsKey(USRNM)
				&& UserSettings.savedSharedPreferences.containsKey(PASSWD)) {
			return true;

		} else
			return false;

	}

	public static void update() {

		UserSettings.savedSharedPreferences = settings.getAll();

	}

	/**
	 * @return the username
	 */
	public static String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public static void setUsername(String username) {

		prefEditor = settings.edit();
		prefEditor.putString(UserSettings.USRNM, username);
		prefEditor.commit();
		UserSettings.username = username;
	}

	/**
	 * @return the password
	 */
	public static String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public static void setPassword(String password) {

		prefEditor = settings.edit();
		prefEditor.putString(UserSettings.PASSWD, password);
		prefEditor.commit();
		UserSettings.password = password;
	}

	/**
	 * @return the smallMessage
	 */
	public static String getSmallMessage() {
		return smallMessage;
	}

	/**
	 * @param smallMessage
	 *            the smallMessage to set
	 */
	public static void setSmallMessage(String smallMessage) {

		prefEditor = settings.edit();
		prefEditor.putString(UserSettings.SMLLMSSG, smallMessage);
		prefEditor.commit();
		UserSettings.smallMessage = smallMessage;
	}

	/**
	 * @return the date
	 */
	public static String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public static void setDate(String date) {

		prefEditor = settings.edit();
		prefEditor.putString(UserSettings.DATE, date);
		prefEditor.commit();
		UserSettings.date = date;
	}

	/**
	 * @return the searchRadius
	 */
	public static int getSearchRadius() {
		return searchRadius;
	}

	/**
	 * @param searchRadius
	 *            the searchRadius to set
	 */
	public static void setSearchRadius(int searchRadius) {

		prefEditor = settings.edit();
		prefEditor.putInt(UserSettings.SRCHRDS, searchRadius);
		prefEditor.commit();
		UserSettings.searchRadius = searchRadius;
	}

	/**
	 * @return the mapType
	 */
	public static int getMapType() {
		return mapType;
	}

	/**
	 * @param mapType
	 *            the mapType to set
	 */
	public static void setMapType(int mapType) {

		prefEditor = settings.edit();
		prefEditor.putInt(UserSettings.MAPTYPE, mapType);
		prefEditor.commit();
		UserSettings.mapType = mapType;
	}

	/**
	 * @return the autoLogin
	 */
	public static boolean isAutoLogin() {
		return autoLogin;
	}

	/**
	 * @param autoLogin
	 *            the autoLogin to set
	 */
	public static void setAutoLogin(boolean autoLogin) {

		prefEditor = settings.edit();
		prefEditor.putBoolean(UserSettings.AUTOLOGIN, autoLogin);
		prefEditor.commit();
		UserSettings.autoLogin = autoLogin;
	}

	/**
	 * @return the autoFill
	 */
	public static boolean isAutoFill() {
		return autoFill;
	}

	/**
	 * @param autoFill
	 *            the autoFill to set
	 */
	public static void setAutoFill(boolean autoFill) {

		prefEditor = settings.edit();
		prefEditor.putBoolean(UserSettings.AUTOFILL, autoFill);
		prefEditor.commit();
		UserSettings.autoFill = autoFill;
	}

}
