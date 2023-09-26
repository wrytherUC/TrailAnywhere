package com.trailanywhere.enterprise.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.trailanywhere.enterprise.dto.Trail;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Contains hardcoded data for unit testing.
 */
@Component
public class TrailServiceStub implements ITrailService {
    /**
     * Fetches a trail with a specified name.
     * @param trailName - Name of the trail being searched for
     * @return - trail
     */
    @Override
    public Trail fetchByTrailName(String trailName) {
        Trail trail = new Trail();
        trail.setTrailID(1);
        trail.setName("Forrest Park");
        return trail;
    }

    /**
     * Fetches all trails by specified difficulty
     * @param difficulty - difficulty selected by user
     * @return - trails
     */
    @Override
    public ArrayList<Trail> fetchByDifficulty(String difficulty) {
        ArrayList<Trail> list = new ArrayList<>();
        Trail trailOne = new Trail();
        Trail trailTwo = new Trail();
        trailOne.setDifficulty("Hard");
        trailTwo.setDifficulty("Hard");
        list.add(trailOne);
        list.add(trailTwo);
        return list;
    }

    /**
     * Fetches all trails with the same zip code
     * @param zipCode - zip code selected by user
     * @return - list containing all trails
     */
    @Override
    public ArrayList<Trail> fetchByZipCode(String zipCode) {
        ArrayList<Trail> list = new ArrayList<>();
        Trail trailOne = new Trail();
        Trail trailTwo = new Trail();
        trailOne.setZipCode("45211");
        trailTwo.setZipCode("45211");
        list.add(trailOne);
        list.add(trailTwo);
        return list;
    }

    /**
     * Fetches weather data with user provided zip code
     * @param zipCode - zip code selected by user
     * @return - JSON containing weather data
     */
    @Override
    public JsonNode getCurrentWeatherByZipCode(String zipCode) {
        Trail trailOne = new Trail();
        trailOne.setZipCode("45211");
        JsonNode coordinateData = getCoordinatesFromZipCode(trailOne.getZipCode());
        String latitude = "";
        String longitude = "";
        return getCurrentWeather(latitude, longitude);
    }

    /**
     * Get coordinates from zip code
     * @param zipCode - user provided zip code
     * @return - JSON containing coordinates
     */
    private JsonNode getCoordinatesFromZipCode(String zipCode) {
        return null;
    }

    /**
     * Fetches weather data with user provided coordinates
     * @param latitude - provided latitude coordinate
     * @param longitude - provided longitude coordinate
     * @return - JSON containing weather data
     */
    @Override
    public JsonNode getCurrentWeather(String latitude, String longitude) {
        return null;
    }
}
