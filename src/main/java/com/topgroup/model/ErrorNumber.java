package com.topgroup.model;

public class ErrorNumber {

	private String errNumber;
	private String description;
	
	@Override
	public String toString() {
		return "ErrorNumber [errNumber=" + errNumber + ", description=" + description + "]";
	}

	public ErrorNumber(String errNumber, String description) {
		super();
		this.errNumber = errNumber;
		this.description = description;
	}
	
	public ErrorNumber() {
		super();
	}
	
	public String getErrNumber() {
		return errNumber;
	}
	public void setErrNumber(String errNumber) {
		this.errNumber = errNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
