package com.trailanywhere.enterprise.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.trailanywhere.enterprise.dto.Trail;

import java.util.ArrayList;

public interface ITrailService {
    /**
     * Fetch a trail object based on the name input
     * @param trailName - Name of the trail being searched for
     * @return - return the matching trail
     */
    Trail fetchByTrailName(String trailName);

    /**
     * Add a new trail
     * @param trail - new trail
     */
    void addTrail(Trail trail);

    /**
     * Fetch all trails
     */
    ArrayList<Trail> fetchAlltrails();

    /**
     * Fetch all trails that match difficulty
     * @param difficulty - difficulty selected by user
     * @return - all trails with the same specified difficulty
     */
    ArrayList<Trail> fetchByDifficulty(String difficulty);

    /**
     * Fetch all trails with matching zip codes
     * @param zipCode - zip code selected by user
     * @return - all trails with the same zip code
     */
    ArrayList<Trail> fetchByZipCode(String zipCode);

    /**
     * Get current weather data for trails with provided zip code
     * @param zipCode - zip code selected by user
     * @return - JSON containing weather information
     */
    JsonNode getCurrentWeatherByZipCode(String zipCode);

    /**
     * Fetch weather data with provided coordinates
     * @param latitude - provided latitude coordinate
     * @param longitude - provided longitude coordinate
     * @return - JSON containing weather information
     */
    JsonNode getCurrentWeather(String latitude, String longitude);

    /**
     * Fetch all trails with the same coordinates
     * @param latitude - provided latitude coordinate
     * @param longitude - provided longitude coordinate
     * @return - List containing all trails
     */
    ArrayList<Trail> fetchByCoordinates(String latitude, String longitude);

    Trail save(Trail trail) throws Exception;
}
