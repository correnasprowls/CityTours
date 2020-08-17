package com.techelevator.model;

public class Landmark {

	private int landmarkId;
	private String landmarkName;
	private String landmarkDescription;
	private int locationId;
	private Location location;
	public int getLandmarkId() {
		return landmarkId;
	}
	public void setLandmarkId(int landmarkId) {
		this.landmarkId = landmarkId;
	}
	public String getLandmarkName() {
		return landmarkName;
	}
	public void setLandmarkName(String landmarkName) {
		this.landmarkName = landmarkName;
	}
	public String getLandmarkDescription() {
		return landmarkDescription;
	}
	public void setLandmarkDescription(String landmarkDescription) {
		this.landmarkDescription = landmarkDescription;
	}
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((landmarkDescription == null) ? 0 : landmarkDescription.hashCode());
		result = prime * result + landmarkId;
		result = prime * result + ((landmarkName == null) ? 0 : landmarkName.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + locationId;
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
		Landmark other = (Landmark) obj;
		if (landmarkDescription == null) {
			if (other.landmarkDescription != null)
				return false;
		} else if (!landmarkDescription.equals(other.landmarkDescription))
			return false;
		if (landmarkId != other.landmarkId)
			return false;
		if (landmarkName == null) {
			if (other.landmarkName != null)
				return false;
		} else if (!landmarkName.equals(other.landmarkName))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (locationId != other.locationId)
			return false;
		return true;
	}


	
	
}
