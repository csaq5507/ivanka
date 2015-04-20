/**
 * 
 */
package edu.wastkosch.models;

import java.sql.ResultSet;

/**
 * @author binary
 *
 */
public class User {

	private String username;
	private String password;
	private String email;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User(ResultSet resultSet) {

	}

	public boolean authenticate(String string) {
		return string.equals(password);
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
