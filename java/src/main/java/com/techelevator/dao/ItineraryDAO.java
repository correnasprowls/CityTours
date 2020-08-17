package com.techelevator.dao;


import java.security.Principal;
import java.util.List;

import com.techelevator.model.Itinerary;
import com.techelevator.model.Landmark;

public interface ItineraryDAO {

	List<Itinerary> getItineraryByUsername(String username);
	
	Itinerary createItinerary(Itinerary newItinerary);
	
	void deleteItinerary(int id);
	
	void updateItineraryName(int id);
	
	void removeLandmarkFromItinerary(int landmarkId, int itineraryId);
	
	void addLandmarkToItinerary(int landmarkId, int itineraryId);
}
