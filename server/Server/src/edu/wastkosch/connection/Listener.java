/**
 * 
 */
package edu.wastkosch.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Ivan
 *
 */
public class Listener extends Thread {

	private ServerSocket serverSocket;

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(Protocol.PORT);
		} catch (IOException e) {
			System.out.println("Could not create server Socket!");
			e.printStackTrace();
			return;
		}
		while (true) {
			try {
				Socket client = serverSocket.accept();
				ClientConnection clientCon = new ClientConnection(client);
				clientCon.start();
			} catch (IOException e) {
				System.out.println("Could not connect to Client");
				e.printStackTrace();
			}
		}
	}
}
