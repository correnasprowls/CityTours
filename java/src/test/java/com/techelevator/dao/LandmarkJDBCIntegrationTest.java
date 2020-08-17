package com.techelevator.dao;



import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Coordinates;
import com.techelevator.model.Landmark;
import com.techelevator.model.Location;




public class LandmarkJDBCIntegrationTest extends DAOIntegrationTest {
	
	private JdbcTemplate jdbcTemplate;
	private LandmarkDAO landmarkDAO;
	@Before
	public void setup() {
		this.jdbcTemplate = new JdbcTemplate(super.getDataSource());
		this.landmarkDAO = new LandmarkJdbc(jdbcTemplate);
	}
	
	@Test
	public void get_landmark_by_id_test() {
		Landmark landmark = insertNewLandmark(createTestLandmark(insertNewLocation(createTestLocation(createTestCoordinates()))));
		Landmark actualLandmark = landmarkDAO.getLandmarkById(landmark.getLandmarkId());
		Assert.assertEquals(landmark.getLandmarkName(), actualLandmark.getLandmarkName());
		Assert.assertEquals(landmark.getLandmarkDescription(), actualLandmark.getLandmarkDescription());
		Assert.assertEquals(landmark.getLocationId(), actualLandmark.getLocationId());
		Assert.assertEquals( "zipcode", actualLandmark.getLocation().getZipCode());
	}
	
	@Test
	public void add_new_landmark_test_added_landmark() {
		int originalSize = getLandmarkListLength();
		Landmark landmark = createTestLandmark(createTestLocation(createTestCoordinates()));
		landmarkDAO.addLandmark(landmark);
		int newLength = getLandmarkListLength();
		Assert.assertEquals(originalSize + 1, newLength);
		
	}
	
	@Test
	public void add_new_landmark_test_added_location() {
		int originalSize = getLocationListLength();
		Landmark landmark = createTestLandmark(createTestLocation(createTestCoordinates()));
		landmarkDAO.addLandmark(landmark);
		int newLength = getLocationListLength();
		
		Assert.assertEquals(originalSize + 1, newLength);
		
	}
	
	@Test
	public void add_new_landmark_test_added_coordinates() {
		int originalSize = getCoordinatesListLength();
		Landmark landmark = createTestLandmark(createTestLocation(createTestCoordinates()));
		landmarkDAO.addLandmark(landmark);
		int newLength = getLocationListLength();
		Assert.assertEquals(originalSize + 1, newLength);
		
	}
	
	@Test
	public void get_landmarks_within_distance_test() {
		Landmark landmark = createTestLandmark(createTestLocation(createTestCoordinates()));
		Coordinates coordinatesAntarctica = new Coordinates();
		coordinatesAntarctica.setX(-68.83333);
		coordinatesAntarctica.setY(-90.583331);
		Landmark landmark2 = createTestLandmark(createTestLocation(coordinatesAntarctica));
		int originalSize = landmarkDAO.getLandmarksWithinDistance(createTestCoordinates(), 10).size();
		landmarkDAO.addLandmark(landmark);
		landmarkDAO.addLandmark(landmark2);
		int newSize = landmarkDAO.getLandmarksWithinDistance(createTestCoordinates(), 10).size();
		
		Assert.assertEquals(originalSize + 1, newSize);
		//-68.83333 -90.583331 coordinates of antarctica
	}
	
	private Landmark createTestLandmark(Location location) {
		Landmark landmark = new Landmark();
		landmark.setLandmarkName("Test");
		landmark.setLandmarkDescription("Test Description");
		if (location.getLocationId() != 0) {
			landmark.setLocationId(location.getLocationId());
		}
		landmark.setLocation(location);
		return landmark;
	}
	
	private Location createTestLocation(Coordinates coordinates) {
		Location location = new Location();
		location.setAddressLineOne("address line one");
		location.setCityName("city name");
		location.setStateCode("OH");
		location.setZipCode("zipcode");
		location.setCoordinates(coordinates);
		return location;
	}
	
	private Coordinates createTestCoordinates() {
		Coordinates coordinates = new Coordinates();
		coordinates.setX(38.833289);
		coordinates.setY(-79.422374);
		return coordinates;
	}
	
	private Location insertNewLocation(Location location) {
		String insertSqlLocation = "INSERT INTO location (location_id, address_line_one, city, state_code, zip_code) VALUES (DEFAULT, ?, ?, ?, ?) returning location_id";
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(insertSqlLocation, location.getAddressLineOne(), location.getCityName(), location.getStateCode(), location.getZipCode());
		while(rowSet.next()) {
			location.setLocationId(rowSet.getInt("location_id"));
		}
		return location;
	}
	
	private Landmark insertNewLandmark(Landmark landmark) {
		String insertSqlLandmark = "INSERT INTO landmarks (landmark_id, name, description, location_id) VALUES (DEFAULT, ?, ?, ?) returning landmark_id";
		
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(insertSqlLandmark, landmark.getLandmarkName(), landmark.getLandmarkDescription(), landmark.getLocationId());
		
		while(rowSet.next()) {
			landmark.setLandmarkId(rowSet.getInt("landmark_id"));
		}
		return landmark;
	}
	
	private int getLandmarkListLength() {
		List<Integer> ids = new ArrayList<Integer>();
		String SelectSql = "SELECT landmark_id, name, description, location_id FROM landmarks";
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(SelectSql);
		while(rowSet.next()) {
			ids.add(rowSet.getInt("landmark_id"));
		}
		return ids.size();
	}
	
	private int getLocationListLength() {
		List<Integer> ids = new ArrayList<Integer>();
		String SelectSql = "SELECT location_id FROM location";
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(SelectSql);
		while(rowSet.next()) {
			ids.add(rowSet.getInt("location_id"));
		}
		return ids.size();
	}
	
	private int getCoordinatesListLength() {
		List<Integer> ids = new ArrayList<Integer>();
		String SelectSql = "SELECT coordinate_id FROM coordinates";
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(SelectSql);
		while(rowSet.next()) {
			ids.add(rowSet.getInt("coordinate_id"));
		}
		return ids.size();
	}
}
