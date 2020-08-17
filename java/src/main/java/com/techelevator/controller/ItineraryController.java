package com.techelevator.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.dao.ItineraryDAO;
import com.techelevator.email.Email;
import com.techelevator.model.Itinerary;
import com.techelevator.model.ItineraryEmail;
import com.techelevator.model.Landmark;


@RestController
public class ItineraryController {

	private ItineraryDAO itineraryDao;
	
	public ItineraryController(ItineraryDAO itineraryDao) {
		this.itineraryDao = itineraryDao;
	}
	
	@CrossOrigin
	@RequestMapping(value= "/itineraries", method = RequestMethod.GET)
	public List<Itinerary> getItineraryByUsername(Principal principal) {
		return itineraryDao.getItineraryByUsername(principal.getName());
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/itineraries", method = RequestMethod.POST)
	public Itinerary createItinerary(@RequestBody Itinerary newItinerary, Principal principal) {
		newItinerary.setUsername(principal.getName());
		return itineraryDao.createItinerary(newItinerary);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/itineraries/{id}", method = RequestMethod.DELETE)
	public void deleteItinerary(@PathVariable int id) {
		itineraryDao.deleteItinerary(id);
	}
	
	@CrossOrigin
	@RequestMapping(value = "landmarks/{id}/itineraries/{itineraryId}", method = RequestMethod.DELETE)
	public void removeLandmarkFromItinerary(@PathVariable int id, @PathVariable int itineraryId) {
		itineraryDao.removeLandmarkFromItinerary(id, itineraryId);
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "landmarks/{id}/itineraries/{itineraryId}", method = RequestMethod.POST)
	public void addLandmarkToItinerary(@PathVariable int id, @PathVariable int itineraryId) {
		itineraryDao.addLandmarkToItinerary(id, itineraryId);
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "itineraries/email")
	public void sendItineraryToEmail(@RequestBody ItineraryEmail itineraryEmail) {
		Email email = new Email();
		email.sendEmail(itineraryEmail);
		
	}
	
}
