package com.trailanywhere.enterprise.service;

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
}
