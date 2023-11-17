package com.trailanywhere.enterprise.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.trailanywhere.enterprise.dto.Trail;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    void addTrail(Trail trail) throws Exception;

    /**
     * Fetch all trails
     */
    List<Trail> fetchAllTrails();

    /**
     * Fetch all trails that match difficulty
     * @param difficulty - difficulty selected by user
     * @return - all trails with the same specified difficulty
     */
    List<Trail> fetchByDifficulty(String difficulty);

    /**
     * Fetch all trails with matching zip codes
     * @param zipCode - zip code selected by user
     * @return - all trails with the same zip code
     */
    List<Trail> fetchByZipCode(String zipCode);

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
    Trail fetchByCoordinates(String latitude, String longitude);

    /**
     * Saves a trail
     * @param trail - trail to be saved
     * @return - new trail
     * @throws Exception - handle errors
     */
    Trail save(Trail trail) throws Exception;

    /**
     * Deletes a trail
     * @param trailID - trail to be deleted
     * @throws Exception - handle errors
     */
    void delete(int trailID) throws Exception;

    /**
     * Find a trail based on its ID
     * @param trailID - trail ID
     * @return - trail
     */
    Trail findTrailByID(int trailID);

    List<Trail> fetchByTrailType(String trailType);

    Set<String> fetchAllDifficultyTypes();
}
