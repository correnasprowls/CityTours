package com.techelevator.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.jr.ob.*;

import com.techelevator.dao.LandmarkDAO;
import com.techelevator.model.Coordinates;
import com.techelevator.model.Forecast;
import com.techelevator.model.Landmark;
import com.techelevator.model.Location;

@RestController
public class LandmarkController {
	
	private LandmarkDAO landmarkDao;
	public LandmarkController(LandmarkDAO landmarkDao) {
		this.landmarkDao = landmarkDao;
	}
	@CrossOrigin
	@RequestMapping(value = "/landmarks/{id}", method = RequestMethod.GET)
	public Landmark getLandmarkById(@PathVariable int id ) {
		Landmark landmark = landmarkDao.getLandmarkById(id);
		Coordinates coordinates = getCoordinates(landmark.getLocation());
		landmark.getLocation().setCoordinates(coordinates);
		return landmark;
	}
	@CrossOrigin
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/landmarks", method = RequestMethod.POST)
	public Landmark addLandmark(@RequestBody Landmark landmark) {
		Coordinates coordinates = getCoordinates(landmark.getLocation());
		landmark.getLocation().setCoordinates(coordinates);
		return landmarkDao.addLandmark(landmark);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/landmarks/distanceLTE{distance}", method = RequestMethod.POST)
	public List<Landmark> getLandmarksWithinDistance( @PathVariable int distance, @RequestBody Location location) {
		Coordinates coordinates = getCoordinates(location);
		List<Landmark> landmarks = landmarkDao.getLandmarksWithinDistance(coordinates, distance);
		return landmarks;
	}
	
	@CrossOrigin
	@RequestMapping(value = "/coordinates", method= RequestMethod.POST)
	public Location getLocationWithCoordinates(@RequestBody Location location) {
		Coordinates coordinates = getCoordinates(location);
		location.setCoordinates(coordinates);
		return location;
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/landmarks/forecasts", method = RequestMethod.POST)
	public Forecast getForcastByLandmark(@RequestBody Location location) {
		return getForecast(location.getCoordinates());
	}

	private Coordinates getCoordinates(Location location) {
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject("https://geocoding.geo.census.gov/geocoder/locations/address?street=" 
				+ location.getAddressLineOne().replaceAll(" ", "+") 
				+ "&city=" + location.getCityName() 
				+ "&state=" + location.getStateCode() 
				+ "&zip=" + location.getZipCode() 
				+ "&benchmark=Public_AR_Census2010&format=json", String.class);
		Map<String,Object> map = null;
		try {
			map = JSON.std.mapFrom(response);
		} catch (JSONObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String,Object> map2 = (Map<String, Object>) map.get("result");
		List<Object> list = (List<Object>) map2.get("addressMatches");
		Map<String,Object> map3 = (Map<String, Object>) list.get(0);
		Map<String,Object> map4 = (Map<String, Object>) map3.get("coordinates");
		double x = (double) map4.get("x");
		double y = (double) map4.get("y");
		Coordinates coordinates = new Coordinates();
		coordinates.setX(x);
		coordinates.setY(y);
		return coordinates;
	}
	
	private Forecast getForecast(Coordinates coordinates) {
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject("https://api.openweathermap.org/data/2.5/onecall?units=imperial&exclude=current,minutely,hourly&"
				+ "lat=" + coordinates.getY() + "&lon=" + coordinates.getX() + "&appid=e28822e048a496f25428fbf52e7beb11", String.class);
		Map<String,Object> initialMap = null;
		try {
			initialMap = JSON.std.mapFrom(response);
		} catch (JSONObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Object> daily =  (List<Object>) initialMap.get("daily");
		Map<String,Object> dayOne = (Map<String, Object>) daily.get(0);
		Map<String,Object> dayOneTemp = (Map<String, Object>) dayOne.get("temp");
		double dayOneMin = Double.valueOf(dayOneTemp.get("min").toString());
		double dayOneMax = Double.valueOf(dayOneTemp.get("max").toString());
		List<Object> dayOneWeather =  (List<Object>) dayOne.get("weather");
		Map<String,Object> dayOneWeatherObj = (Map<String, Object>) dayOneWeather.get(0);
		String description = (String) dayOneWeatherObj.get("main");
		String icon = (String) dayOneWeatherObj.get("icon");
		Forecast forecast = new Forecast( dayOneMax, dayOneMin, icon, description);
		return forecast;
	}

}
