package com.techelevator.dao;

import java.util.List;

import com.techelevator.model.Coordinates;
import com.techelevator.model.Landmark;

public interface LandmarkDAO {
	
	Landmark getLandmarkById(int id);
	
	Landmark addLandmark(Landmark newLandmark);
	
	List<Landmark> getLandmarksWithinDistance(Coordinates coordinates, int distance);
}
