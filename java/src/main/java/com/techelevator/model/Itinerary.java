package com.techelevator.model;

import java.util.List;

public class Itinerary {

	private String itineraryName;
	private int itineraryId;
	private int landmarkId;
	private List<Landmark> landmarks;
	private String username;
	private String color;
	public String getItineraryName() {
		return itineraryName;
	}
	public void setItineraryName(String itineraryName) {
		this.itineraryName = itineraryName;
	}
	public int getItineraryId() {
		return itineraryId;
	}
	public void setItineraryId(int itineraryId) {
		this.itineraryId = itineraryId;
	}
	public int getLandmarkId() {
		return landmarkId;
	}
	public void setLandmarkId(int landmarkId) {
		this.landmarkId = landmarkId;
	}
	public List<Landmark> getLandmarks() {
		return landmarks;
	}
	public void setLandmarks(List<Landmark> landmarks) {
		this.landmarks = landmarks;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + itineraryId;
		result = prime * result + ((itineraryName == null) ? 0 : itineraryName.hashCode());
		result = prime * result + landmarkId;
		result = prime * result + ((landmarks == null) ? 0 : landmarks.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Itinerary other = (Itinerary) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (itineraryId != other.itineraryId)
			return false;
		if (itineraryName == null) {
			if (other.itineraryName != null)
				return false;
		} else if (!itineraryName.equals(other.itineraryName))
			return false;
		if (landmarkId != other.landmarkId)
			return false;
		if (landmarks == null) {
			if (other.landmarks != null)
				return false;
		} else if (!landmarks.equals(other.landmarks))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
	
}
