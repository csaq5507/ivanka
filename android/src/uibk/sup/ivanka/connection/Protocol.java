/**
 * 
 */
package uibk.sup.ivanka.connection;

/**
 * @author Ivan Waldboth
 *
 */
public class Protocol {
	public static final int PORT = 2404;
	public static final String REGISTER = "::REGISTER::"; // username, passwd,
															// EMAIL, IMEI
	public static final String LOGIN = "::LOGIN::"; // usrname, passwd,
	public static final String GET_EVENT_STREAM = "::GET_EVENT_STREAM::"; // Lat,
																			// Long,
	// radius,
	// music?,
	// rating?,
	// size?
	public static final String FILTER_MUSIC = "::FILTER_MUSIC::"; // genre
	public static final String FILTER_RATING = "::FILTER_RATING::"; // > int
	public static final String FILTER_SIZE = "::FILTER_SIZE::"; // > int
	public static final String GET_EVENT = "::GET_EVENT::"; // id
	public static final String IMAGE_START = "::IMAGE_START::";
	public static final String IMAGE_END = "::IMAGE_END::";

	public static final String LOGOUT = "::LOGOUT::"; // nichts
	public static final String WELCOME = "::HELLO::"; // publicKEY n, E
	public static final String PUBLIC_KEY_START = "::PUBLIC_KEY_START::";
	public static final String PUBLIC_KEY_END = "::PUBLIC_KEY_END::";
	public static final String ADD_EVENT = "::ADD_EVENT::"; // title, lat, long,
	// bewertung, start!,
	// end!, size!, foto!,
	// foto?, foto?, ...
	public static final String UPDATE_EVENT = "UPDATE_EVENT"; // id!,
	// bewertung?,
	// kommentar?,
	// foto?, foto?,
	// ...
	public static final String UPDATE_EVENT_COMMENT = "COMMENT";
	public static final String UPDATE_EVENT_RATING = "RATING";
	public static final String UPDATE_EVENT_FOTO = "FOTO";

	public static final String REPORT = "REPORT"; // id, comment
	public static final String GET_HISTORY_STREAM = "HISTORY_STREAM"; // lat,
																		// lng,
																		// time

	public static final RSA privateKey = new RSA(32);
	public static final String SUCCESS = "SUCCESS";
	public static final String FAILURE = "FAILURE";
	public static final String DOS = "Denial_of_Service";

	public static final String MESSAGE_SEPERATOR = ":Seperator:";

	public static final String SERVER_HOST = "vm.libac.at";

}
