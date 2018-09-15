package com.jpm1590.model;

public class DateModel {
	
	private String newDate;

	public DateModel(String newDate) {
		super();
		this.newDate = newDate;
	}

	public DateModel() {
		super();
	}

	public String getNewDate() {
		return newDate;
	}

	public void setNewDate(String newDate) {
		this.newDate = newDate;
	}

	@Override
	public String toString() {
		return "DateModel [newDate=" + newDate + "]";
	}

	
	
}
