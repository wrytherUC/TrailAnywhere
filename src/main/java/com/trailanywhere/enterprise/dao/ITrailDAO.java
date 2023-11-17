package com.trailanywhere.enterprise.dao;

import com.trailanywhere.enterprise.dto.Trail;

import java.util.List;
import java.util.Set;

/**
 * Contains CRUD methods for trails
 */
public interface ITrailDAO {
    /**
     * Saves a trail
     * @param trail - trail to be saved
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
     * Fetch all trails
     */
    List<Trail> fetchAllTrails();

    /**
     * Fetch a trail with a specified name
     * @param trailName - name
     * @return - trail
     */
    Trail fetchByTrail(String trailName);

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
     * Fetch all trails with the same coordinates
     * @param latitude - provided latitude coordinate
     * @param longitude - provided longitude coordinate
     * @return - List containing all trails
     */
    Trail fetchByCoordinates(String latitude, String longitude);

    /**
     * Find a trail based on its ID
     * @param trailID - trail ID
     * @return - trail
     */
    Trail findTrailByID(int trailID);

    List<Trail> fetchByTrailType(String trailType);

    List<String> fetchAllDifficultyTypes();

    List<String> fetchAllTrailTypes();
}
