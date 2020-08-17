package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import com.techelevator.model.Coordinates;
import com.techelevator.model.Landmark;
import com.techelevator.model.Location;
import com.techelevator.calculator.DistanceCalculator;

@Component
public class LandmarkJdbc implements LandmarkDAO{

	private JdbcTemplate jdbcTemplate;
	private DistanceCalculator distCalc;
	
	public LandmarkJdbc(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.distCalc = new DistanceCalculator();
	}
	
	@Override
	public Landmark getLandmarkById(int id) {
		Landmark landmark = new Landmark();
		Location location = new Location();
		
		String selectSql = "SELECT landmarks.landmark_id, landmarks.name, landmarks.description, landmarks.location_id, address_line_one, city, state_code, zip_code FROM landmarks JOIN location on landmarks.location_id = location.location_id WHERE landmarks.landmark_id = ?";
		
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(selectSql, id);
		while(rowSet.next()) {
			landmark = mapRowToLandmark(rowSet);
			location = mapRowToLocation(rowSet);
			landmark.setLocation(location);
		}
		
		return landmark;
	}

	@Override
	public Landmark addLandmark(Landmark newLandmark) {
		String coordinateInsertSql = "INSERT INTO coordinates (coordinate_id, x, y) VALUES (DEFAULT, ?, ?) RETURNING coordinate_id";
		SqlRowSet rowSetCoordinate = jdbcTemplate.queryForRowSet(coordinateInsertSql, newLandmark.getLocation().getCoordinates().getX(), newLandmark.getLocation().getCoordinates().getY() );
		int coordinateId = 0;
		while(rowSetCoordinate.next()) {
			coordinateId = rowSetCoordinate.getInt("coordinate_id");
		}
		
		String locationInsertSql = "INSERT INTO location (location_id, address_line_one, city, state_code, zip_code, coordinate_id) "
				+ "VALUES (DEFAULT, ?, ?, ?, ?, ?) returning location_id";
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(locationInsertSql, 
				newLandmark.getLocation().getAddressLineOne(), 
				newLandmark.getLocation().getCityName(), 
				newLandmark.getLocation().getStateCode(), 
				newLandmark.getLocation().getZipCode(),
				coordinateId);
		int locationId = 0;
		while(rowSet.next()) {
			locationId = rowSet.getInt("location_id");
		}
		
		String landmarkInsertSql = "INSERT INTO landmarks (landmark_id, name, description, location_id) VALUES (DEFAULT, ?, ?, ?) RETURNING landmark_id";
		SqlRowSet rowSet2 = jdbcTemplate.queryForRowSet(landmarkInsertSql, newLandmark.getLandmarkName(), 
				newLandmark.getLandmarkDescription(), locationId);
		while(rowSet2.next()) {
			newLandmark.setLandmarkId(rowSet2.getInt("landmark_id"));
		}
		return newLandmark;
	}
	
	@Override
	public List<Landmark> getLandmarksWithinDistance(Coordinates coordinates, int distance) {
		List<Landmark> landmarks = new ArrayList<Landmark>();
		String selectSql = "SELECT landmarks.landmark_id, landmarks.name, landmarks.description, landmarks.location_id, address_line_one, city, state_code, zip_code, x, y FROM landmarks\n" + 
				"JOIN location on landmarks.location_id = location.location_id\n" + 
				"JOIN coordinates on location.coordinate_id = coordinates.coordinate_id";
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(selectSql);
		while(rowSet.next()) {
			Coordinates coordinatesFromDB = mapRowToCoordinates(rowSet);
			if (distance < distCalc.distance(coordinates.getX(), coordinates.getY(), coordinatesFromDB.getX(), coordinatesFromDB.getY(), "M")) {
				continue;
			}
			Location location = mapRowToLocation(rowSet);
			Landmark landmark = mapRowToLandmark(rowSet);
			location.setCoordinates(coordinatesFromDB);
			landmark.setLocation(location);
			landmarks.add(landmark);
		}
		return landmarks;
	}
	
	private Landmark mapRowToLandmark(SqlRowSet rowSet) {
		Landmark landmark = new Landmark();
		landmark.setLandmarkName(rowSet.getString("name"));
		landmark.setLandmarkDescription(rowSet.getString("description"));
		landmark.setLocationId(rowSet.getInt("location_id"));
		landmark.setLandmarkId(rowSet.getInt("landmark_id"));
		return landmark;
	}
	
	private Location mapRowToLocation(SqlRowSet rowSet) {
		Location location = new Location();
		location.setAddressLineOne(rowSet.getString("address_line_one"));
		location.setCityName(rowSet.getString("city"));
		location.setLocationId(rowSet.getInt("location_id"));
		location.setStateCode(rowSet.getString("state_code"));
		location.setZipCode(rowSet.getString("zip_code"));
		
		return location;
	}
	
	private Coordinates mapRowToCoordinates(SqlRowSet rowSet) {
		Coordinates coordinates = new Coordinates();
		coordinates.setX(rowSet.getDouble("x"));
		coordinates.setY(rowSet.getDouble("y"));
		return coordinates;
	}


}
