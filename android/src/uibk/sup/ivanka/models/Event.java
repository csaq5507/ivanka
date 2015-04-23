package uibk.sup.ivanka.models;

import java.util.Date;

public class Event {
	private String name;
	private Date start;
	private Date end;
	private double longitude;
	private double latitude;

	/**
	 * @param name
	 * @param start
	 * @param end
	 * @param longitude
	 * @param latitude
	 */
	public Event(String name, Date start, Date end, double latitude,
			double longitude) {
		super();
		this.name = name;
		this.start = start;
		this.end = end;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public Event(String event) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		String result = "";
		result += ":NEW_ATTR:";
		result += name;
		result += ":NEW_ATTR:";
		result += start.getTime();
		result += ":NEW_ATTR:";
		result += end.getTime();
		result += ":NEW_ATTR:";
		result += latitude;
		result += ":NEW_ATTR:";
		result += longitude;
		return result;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(Date end) {
		this.end = end;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

}
