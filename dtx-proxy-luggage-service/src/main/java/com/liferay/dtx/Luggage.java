package com.liferay.dtx;

public class Luggage {

	public long id;
	public String status;
	public Long timestamp;

	public Luggage(long id, String status, Long timestamp) {
		super();
		this.id = id;
		this.status = status;
		this.timestamp = timestamp;
	}

	public long getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	public Long getTimestamp() {
		return timestamp;
	}
}
