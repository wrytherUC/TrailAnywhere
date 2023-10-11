package com.trailanywhere.enterprise.dao;

import com.trailanywhere.enterprise.dto.Trail;

import java.util.ArrayList;
import java.util.List;

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
     * @param trail - trail to be deleted
     * @throws Exception - handle errors
     */
    void delete(Trail trail) throws Exception;

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
    List<Trail> fetchByCoordinates(String latitude, String longitude);
}
