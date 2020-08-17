package com.techelevator.model;

public class Forecast {

	private double maxTemp;
	private double minTemp;
	private String icon;
	private String description;
	
	
	
	public Forecast(double maxTemp, double minTemp, String icon, String description) {
		super();
		this.maxTemp = maxTemp;
		this.minTemp = minTemp;
		this.icon = icon;
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


	public double getMaxTemp() {
		return maxTemp;
	}
	public void setMaxTemp(double maxTemp) {
		this.maxTemp = maxTemp;
	}
	public double getMinTemp() {
		return minTemp;
	}
	public void setMinTemp(double minTemp) {
		this.minTemp = minTemp;
	}
	public void setMinTemp(int minTemp) {
		this.minTemp = minTemp;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
}
