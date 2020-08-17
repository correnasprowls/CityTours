package com.techelevator.dao;


import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.Coordinates;
import com.techelevator.model.Itinerary;
import com.techelevator.model.Landmark;
import com.techelevator.model.Location;



@Component
public class ItineraryJdbc implements ItineraryDAO {
	
	
	private JdbcTemplate jdbcTemplate;

	public ItineraryJdbc(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<Itinerary> getItineraryByUsername(String username) {
		List<Itinerary> itineraries = new ArrayList<>();
		
		String selectSql = "SELECT itineraries.itinerary_id, itineraries.name, itineraries.username, itineraries.landmark_id, itineraries.color FROM itineraries JOIN users on itineraries.username = users.username WHERE users.username = ? ORDER BY itineraries.itinerary_id DESC";
		
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(selectSql, username);
		
		while(rowSet.next()) {
			itineraries.add(mapRowToItinerary(rowSet));	
		}
		
		for(Itinerary itinerary : itineraries) {
			itinerary.setLandmarks(getLandmarkDetailsByItinerary(itinerary));
		}
		
		return itineraries;
	}

	@Override
	public Itinerary createItinerary(Itinerary newItinerary) {
		
		String insertSql = "INSERT INTO itineraries (itinerary_id, username, name, landmark_id, color) VALUES (DEFAULT, ?, ?, ?, ?) returning itinerary_id, username, name, landmark_id, color";
		
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(insertSql,
				newItinerary.getUsername(),
				newItinerary.getItineraryName(),
				newItinerary.getLandmarkId(),
				newItinerary.getColor());
		
		while(rowSet.next()) {
			newItinerary = mapRowToItinerary(rowSet);
		}
		return newItinerary;
	}
	
	@Override
	public void deleteItinerary(int id) {
		String sqlDeleteJoin = "DELETE FROM public.landmarks_itineraries WHERE itinerary_id = ?";
		String sqlDelete = "DELETE FROM public.itineraries WHERE itinerary_id = ?";
		jdbcTemplate.update(sqlDeleteJoin, id);
		jdbcTemplate.update(sqlDelete, id);
	}
	
	@Override
	public void updateItineraryName(int id) {
		String sqlUpdateName ="UPDATE itineraries SET name = ? WHERE itinerary_id = ?";
		
	}
	
	@Override
	public void removeLandmarkFromItinerary(int landmarkId, int itineraryId) {
		String deleteSql = "DELETE FROM landmarks_itineraries WHERE landmark_id = ? and itinerary_id = ?";
		
		jdbcTemplate.update(deleteSql, landmarkId, itineraryId );
	}
	
	@Override
	public void addLandmarkToItinerary(int landmarkId, int itineraryId) {
		String sqlInsert = "INSERT INTO public.landmarks_itineraries (landmark_id, itinerary_id) VALUES (?, ?)";
		jdbcTemplate.update(sqlInsert, landmarkId, itineraryId);
		
	}
	
	private List<Landmark> getLandmarkDetailsByItinerary(Itinerary itinerary) {
		List<Landmark> landmarks = new ArrayList<Landmark>();
		String selectSql = "SELECT landmarks.landmark_id, landmarks.name, landmarks.description, landmarks.location_id, location.address_line_one, location.city, location.state_code, location.zip_code, coordinates.x, coordinates.y " + 
				"FROM itineraries " + 
				"JOIN landmarks_itineraries ON itineraries.itinerary_id = landmarks_itineraries.itinerary_id " + 
				"JOIN landmarks ON landmarks_itineraries.landmark_id = landmarks.landmark_id " + 
				"JOIN location ON landmarks.location_id = location.location_id " +
				"JOIN coordinates ON location.coordinate_id = coordinates.coordinate_id " +
				"WHERE itineraries.itinerary_id = ?";
		
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(selectSql, itinerary.getItineraryId());
		
		while (rowSet.next()) {
			Landmark landmarkToAdd = new Landmark();
			Location location = new Location();
			Coordinates coordinates = new Coordinates();
			coordinates.setX(rowSet.getDouble("x"));
			coordinates.setY(rowSet.getDouble("y"));
			
			location.setAddressLineOne(rowSet.getString("address_line_one"));
			location.setCityName(rowSet.getString("city"));
			location.setStateCode(rowSet.getString("state_code"));
			location.setZipCode(rowSet.getString("zip_code"));
			location.setCoordinates(coordinates);
			
			landmarkToAdd.setLandmarkName(rowSet.getString("name"));
			landmarkToAdd.setLandmarkDescription(rowSet.getString("description"));
			landmarkToAdd.setLandmarkId(rowSet.getInt("landmark_id"));
			landmarkToAdd.setLocationId(rowSet.getInt("location_id"));
			landmarkToAdd.setLocation(location);
			landmarks.add(landmarkToAdd);
		}
		return landmarks;
	}
	
	private Itinerary mapRowToItinerary(SqlRowSet rowSet) {
		Itinerary itinerary = new Itinerary();
		itinerary.setItineraryId(rowSet.getInt("itinerary_id"));
		itinerary.setUsername(rowSet.getString("username"));
		itinerary.setItineraryName(rowSet.getString("name"));
		itinerary.setLandmarkId(rowSet.getInt("landmark_id"));
		itinerary.setColor(rowSet.getString("color"));
		
		
		return itinerary;
	}



	

	

	
}
