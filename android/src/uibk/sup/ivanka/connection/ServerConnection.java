/**
 * 
 */
package uibk.sup.ivanka.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import uibk.sup.ivanka.models.Event;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author Daniel
 *
 */
public class ServerConnection extends AsyncTask<String, String, String> {

	private Socket serverSocket;
	private InputStream in;
	private OutputStream out;
	private RSA publicKey;

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean connect() {
		try {
			serverSocket = new Socket(Protocol.SERVER_HOST, Protocol.PORT);
			in = serverSocket.getInputStream();
			out = serverSocket.getOutputStream();
			String message = Protocol.WELCOME + Protocol.PUBLIC_KEY_START
					+ Protocol.privateKey.getN() + Protocol.MESSAGE_SEPERATOR
					+ Protocol.privateKey.getE() + Protocol.PUBLIC_KEY_END;
			out.write(message.getBytes());
			byte[] buffer = new byte[1024];
			in.read(buffer);
			message = Arrays.toString(buffer);
			publicKey = new RSA(new BigInteger(
					Protocol.privateKey.decrypt(message).split(
							Protocol.PUBLIC_KEY_START)[1]
							.split(Protocol.PUBLIC_KEY_END)[0]
							.split(Protocol.MESSAGE_SEPERATOR)[0]),
					new BigInteger(Protocol.privateKey.decrypt(message).split(
							Protocol.PUBLIC_KEY_START)[1]
							.split(Protocol.PUBLIC_KEY_END)[0]
							.split(Protocol.MESSAGE_SEPERATOR)[1]));
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public ArrayList<Event> getEventStream(int radius) {
		send(publicKey.encrypt(Protocol.GET_EVENT_STREAM + radius));
		ArrayList<Event> ret = new ArrayList<Event>();
		ret.add(new Event("Hallo"));
		ret.add(new Event("Hallo"));
		ret.get(0).setName("Uni");
		ret.get(0).setLatitude(47.263173);
		ret.get(0).setLongitude(11.384251);
		ret.get(1).setName("Technik");
		ret.get(1).setLatitude(47.263377);
		ret.get(1).setLongitude(11.345289);
		return ret;
	}

	public boolean addEvent(String title, LatLng position) {
		send(publicKey.encrypt(Protocol.ADD_EVENT + title
				+ Protocol.MESSAGE_SEPERATOR + position.latitude
				+ Protocol.MESSAGE_SEPERATOR + position.longitude));
		return awaitAnswer();
	}

	public boolean login(String username, String password) {
		send(publicKey.encrypt(Protocol.LOGIN + username
				+ Protocol.MESSAGE_SEPERATOR + password));
		return awaitAnswer();
	}

	private boolean awaitAnswer() {
		byte[] buffer = new byte[1024];
		try {
			in.read(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String message = Arrays.toString(buffer);
		if (Protocol.privateKey.decrypt(message).equals(Protocol.SUCCESS))
			return true;
		else
			return false;
	}

	private String awaitAnswerString() {
		byte[] buffer = new byte[1024];
		try {
			in.read(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String message = Arrays.toString(buffer);
		return Protocol.privateKey.decrypt(message);
	}

	private boolean send(String message) {
		try {
			out.write(message.getBytes());
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * Constructor ########################################################
	 */
	private static ServerConnection instance = null;

	private ServerConnection() throws SecurityException {
		if (!connect())
			throw new SecurityException();
	}

	public static ServerConnection getInstance() {
		if (instance == null) {
			instance = new ServerConnection();
		}
		return instance;
	}
}
