/**
 * 
 */
package edu.wastkosch.controller;

import edu.wastkosch.connection.Listener;
import edu.wastkosch.db.Database;

/**
 * @author Ivan
 *
 */
public class Controller {

	private Database db;
	/*
	 * ###########################################################
	 * ############### Singelton Constructor #####################
	 * ###########################################################
	 */
	public static Controller instance;

	public static Controller getInstance() {
		if (instance != null)
			return instance;
		else {
			instance = new Controller();
			return instance;
		}
	}

	private Controller() {
	}

	public void init() {
		Thread udpListener = new Thread(new Listener());
		udpListener.start();
		// db = new Database();
	}

	/**
	 * @return the db
	 */
	public Database getDb() {
		return db;
	}

	/**
	 * @param db
	 *            the db to set
	 */
	public void setDb(Database db) {
		this.db = db;
	}

}
