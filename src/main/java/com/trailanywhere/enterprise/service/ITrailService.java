package com.trailanywhere.enterprise.service;

import com.trailanywhere.enterprise.dto.Trail;

public interface ITrailService {
    /**
     * Fetch a trail object based on the name input
     * @param trailName - Name of the trail being searched for
     * @return - return the matching trail
     */
    Trail fetchByTrailName(String trailName);
}
