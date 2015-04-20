/**
 * 
 */
package edu.wastkosch.controller;

/**
 * @author Ivan
 *
 */
public class Main {

	static Controller controller = Controller.getInstance();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Server Application for Ivanka.");
		System.out.println("Starting Services...");
		try {
			controller.init();
		} catch (Exception e) {
			// TODO
		}
		System.out.println("Services started. ");
		System.out.println("Server runnning.");
		System.out.println("[s]hutdown, [r]estart, [c]lose service");
	}

}
