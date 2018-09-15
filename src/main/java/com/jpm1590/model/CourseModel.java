package com.jpm1590.model;

public class CourseModel {

	private String name;
	private String description;
	private int hours;
	private int price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public CourseModel(String name, String description, int hours, int price) {
		super();
		this.name = name;
		this.description = description;
		this.hours = hours;
		this.price = price;
	}

	public CourseModel() {

	}

}
