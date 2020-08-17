package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;


import org.junit.Assert;
import org.junit.Before;
import org.junit.*;
import org.springframework.data.relational.core.conversion.DbAction.Insert;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Itinerary;

public class ItineraryJDBCIntegrationTest extends DAOIntegrationTest {

	private JdbcTemplate jdbcTemplate;
	private ItineraryDAO itineraryDAO;
	
	@Before
	public void setup() {
		this.jdbcTemplate = new  JdbcTemplate(super.getDataSource());
		this.itineraryDAO = new ItineraryJdbc(jdbcTemplate);
	}
	
	@Test
	public void get_itinerary_by_username_test() {
		List<Itinerary> originalItinerary = itineraryDAO.getItineraryByUsername("user");
		itineraryDAO.createItinerary(createTestItinerary());
		List<Itinerary> actualItinerary = itineraryDAO.getItineraryByUsername("user");
		Assert.assertEquals(originalItinerary.size() + 1, actualItinerary.size());
	}
	@Test
	public void create_itinerary_test() {
		Itinerary itinerary = createTestItinerary();
		int originalSize = itineraryDAO.getItineraryByUsername("user").size();
		itinerary = itineraryDAO.createItinerary(itinerary);
		int actualSize = itineraryDAO.getItineraryByUsername("user").size();

		Assert.assertEquals(originalSize + 1, actualSize);
				
	}
	@Test
	public void delete_itinerary_test() {
		Itinerary itinerary = createTestItinerary();
		itinerary = itineraryDAO.createItinerary(itinerary);
		insertJoin(itinerary);
		
		int originalItinerarySize = itineraryDAO.getItineraryByUsername("user").size();
		int originalJoinSize = getJoinLength();
		
		
		itineraryDAO.deleteItinerary(itinerary.getItineraryId());
		Assert.assertEquals(originalItinerarySize - 1, itineraryDAO.getItineraryByUsername("user").size());
		Assert.assertEquals(originalJoinSize - 1, getJoinLength());
		
		
	}
	@Test
	public void delete_landmark_from_itinerary_test() {
		Itinerary itinerary = createTestItinerary();
		itinerary = itineraryDAO.createItinerary(itinerary);
		insertJoin(itinerary);
		int originalJoinSize = getJoinLength();
		
		itineraryDAO.removeLandmarkFromItinerary(itinerary.getLandmarkId(), itinerary.getItineraryId());
		Assert.assertEquals(originalJoinSize - 1, getJoinLength());
	}
	
	
	
	private Itinerary createTestItinerary() {

		Itinerary itinerary = new Itinerary();
		itinerary.setItineraryName("itinerary");
		itinerary.setUsername("user");
		itinerary.setLandmarkId(6);
		return itinerary;
	}
	
	private int getJoinLength() {
		List<Integer> idListJoin = new ArrayList<Integer>();
		String deleteSqlTest = "SELECT landmark_id, itinerary_id FROM landmarks_itineraries";
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(deleteSqlTest);
		while(rowSet.next()) {
			idListJoin.add(rowSet.getInt("itinerary_id"));
		}
		return idListJoin.size();
	}
	
	private void insertJoin(Itinerary itinerary) {
		String insertSql = "INSERT INTO landmarks_itineraries (landmark_id, itinerary_id) VALUES (?, ?)";
		
		jdbcTemplate.update(insertSql, itinerary.getLandmarkId(), itinerary.getItineraryId());
		
	}
}
