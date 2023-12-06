package com.trailanywhere.enterprise.dao;

import com.trailanywhere.enterprise.dto.Trail;

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

    /**
     * Find a trail based on the provided trail type
     * @param trailType - provided string, base search of off trailType
     * @return - list of trails that match the trailType search
     */
    List<Trail> fetchByTrailType(String trailType);

    /**
     * Provide all trail difficulty types found in database
     * @return list of trail difficulty types from all trails
     */
    List<String> fetchAllDifficultyTypes();

    /**
     * Provide all trail types found in database
     * @return list of trail types from all trails
     */
    List<String> fetchAllTrailTypes();
}
