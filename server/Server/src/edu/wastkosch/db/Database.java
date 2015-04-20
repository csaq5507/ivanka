/**
 * 
 */
package edu.wastkosch.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import edu.wastkosch.models.Event;
import edu.wastkosch.models.User;

/**
 * @author binary
 *
 */
public class Database {
	private final static String USERNAME = "ivankamysql";
	private final static String PASSWORD = "ivankamysql1!";
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/EMP";

	private Connection con;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public Database() {
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getStream(String latitude, String longitude, String radiusMeter) {
		try {

			preparedStatement = con.prepareStatement("SELECT * FROM event WHERE start < NOW() AND end > NOW()");
			resultSet = preparedStatement.executeQuery();
			ArrayList<Event> e = new ArrayList<Event>();
			while (resultSet.next()) {
				if (distance(Double.parseDouble(latitude), Double.parseDouble(longitude), resultSet.getDouble(4),
						resultSet.getDouble(5)) < Integer.parseInt(radiusMeter))
					e.add(new Event(resultSet.getString(1), resultSet.getDate(2), resultSet.getDate(3), resultSet
							.getDouble(4), resultSet.getDouble(5)));
			}
			String result = "";
			for (Event event : e) {
				result += ":NEW_EVENT:";
				result += event.toString();
			}
			return result;
		} catch (SQLException e) {
			return null;
		}
	}

	private double distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
		dist = Math.acos(dist);
		dist = Math.toDegrees(dist);
		dist = dist * 60 * 1.1515 * 1.609344;

		return (dist);

	}

	public User findUser(String string) {
		try {
			preparedStatement = con.prepareStatement("SELECT * FROM user WHERE username LIKE ?");
			preparedStatement.setString(1, string);
			resultSet = preparedStatement.executeQuery();
			return new User(resultSet);
		} catch (SQLException e) {
			return null;
		}
	}

	public void register(User u) throws SQLException {
		preparedStatement = con.prepareStatement("INSERT INTO user VALUES(?,?,?,?)");
		preparedStatement.setString(1, u.getUsername());
		preparedStatement.setString(2, u.getPassword());
		preparedStatement.setString(3, u.getEmail());
		preparedStatement.execute();

	}

	public void logout() {
		// TODO Auto-generated method stub

	}
}
