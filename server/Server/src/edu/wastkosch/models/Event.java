package edu.wastkosch.models;

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
	public Event(String name, Date start, Date end, double latitude, double longitude) {
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
}
