/**
 * 
 */
package edu.wastkosch.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Arrays;

import edu.wastkosch.controller.Controller;
import edu.wastkosch.models.User;

/**
 * @author Ivan
 *
 */
public class ClientConnection extends Thread {

	private Socket clientSocket;
	private InputStream in;
	private OutputStream out;
	private int ddos = 0;
	private boolean authenticated = false;
	private RSA publicKey;

	private User user;

	public ClientConnection(Socket clientSocket) throws IOException {
		this.clientSocket = clientSocket;
		in = clientSocket.getInputStream();
		out = clientSocket.getOutputStream();
	}

	@Override
	public void run() {
		while (true) {
			byte[] buffer = new byte[512];
			try {
				in.read(buffer);

			} catch (IOException e) {
				System.out.println("Error during read");
				e.printStackTrace();
				return;
			}
			String message = Arrays.toString(buffer);
			System.out.println("Read: " + message);
			if (message.startsWith(Protocol.WELCOME)) {
				handshake(message.split(Protocol.WELCOME)[1]);
			} else if (Protocol.privateKey.decrypt(message).startsWith(
					Protocol.REGISTER)) {
				register(message.split(Protocol.REGISTER)[1]);
			} else if (Protocol.privateKey.decrypt(message).startsWith(
					Protocol.LOGIN)) {
				login(message.split(Protocol.LOGIN)[1]);
			} else if (Protocol.privateKey.decrypt(message).startsWith(
					Protocol.GET_EVENT_STREAM)) {
				if (authenticated) {
					send(generateStream(message
							.split(Protocol.GET_EVENT_STREAM)[1]));
				} else
					dos();
			} else if (Protocol.privateKey.decrypt(message).startsWith(
					Protocol.LOGOUT)) {
				if (authenticated) {
					logout();
				} else
					dos();
			} else if (Protocol.privateKey.decrypt(message).startsWith(
					Protocol.ADD_EVENT)) {
				if (authenticated) {
					addEvent(Protocol.privateKey.decrypt(message));
				} else
					dos();
			} else {
				dos();
			}
		}

	}

	private void addEvent(String decrypt) {
		// TODO Auto-generated method stub

	}

	private void dos() {
		ddos++;
		if (ddos > 100) {
			try {
				clientSocket.close();
				in.close();
				out.close();
			} catch (IOException e) {
				System.out.println("Could not close Client Thread!");
				e.printStackTrace();
			}
			this.destroy();
		} else
			send(Protocol.DOS);
	}

	private void register(String message) {
		User u = new User(message.split(Protocol.MESSAGE_SEPERATOR)[0],
				message.split(Protocol.MESSAGE_SEPERATOR)[1]);// TODO MAIL; IMEI
		try {
			Controller.getInstance().getDb().register(u);
			send(Protocol.SUCCESS);
		} catch (Exception e) {
			send(Protocol.FAILURE);
		}
	}

	private void login(String message) {
		User u = Controller.getInstance().getDb()
				.findUser(message.split(Protocol.MESSAGE_SEPERATOR)[0]);
		if (u.authenticate(message.split(Protocol.MESSAGE_SEPERATOR)[1])) {
			this.user = u;
			send(Protocol.SUCCESS);
		} else
			send(Protocol.FAILURE);
	}

	private boolean send(String message) {
		try {
			out.write(message.getBytes());
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	private void logout() {
		try {
			Controller.getInstance().getDb().logout();// TODO
		} catch (Exception e) {
			send(Protocol.FAILURE);
		}
		send(Protocol.SUCCESS);
	}

	private void handshake(String message) {
		if (message.contains(Protocol.PUBLIC_KEY_START)
				&& message.contains(Protocol.PUBLIC_KEY_END)) {
			publicKey = new RSA(new BigInteger(
					message.split(Protocol.PUBLIC_KEY_START)[1]
							.split(Protocol.PUBLIC_KEY_END)[0]
							.split(Protocol.MESSAGE_SEPERATOR)[0]),
					new BigInteger(message.split(Protocol.PUBLIC_KEY_START)[1]
							.split(Protocol.PUBLIC_KEY_END)[0]
							.split(Protocol.MESSAGE_SEPERATOR)[1]));
		}
		send(publicKey.encrypt(Protocol.PUBLIC_KEY_START
				+ Protocol.privateKey.getN() + Protocol.MESSAGE_SEPERATOR
				+ Protocol.privateKey.getE() + Protocol.PUBLIC_KEY_END));
	}

	private String generateStream(String message) { // TODO id, tile, rating
		return Controller
				.getInstance()
				.getDb()
				.getStream(message.split(Protocol.MESSAGE_SEPERATOR)[0],
						message.split(Protocol.MESSAGE_SEPERATOR)[1],
						message.split(Protocol.MESSAGE_SEPERATOR)[2]);
	}

}
