package com.trailanywhere.enterprise.dao;

import com.trailanywhere.enterprise.dto.Trail;

/**
 * Contains CRUD methods for trails
 */
public interface ITrailDAO {
    /**
     * Saves a trail
     * @param trail - trail to be saved
     * @return - new trail
     * @throws Exception - handle errors
     */
    Trail save(Trail trail) throws Exception;

    /**
     * Deletes a trail
     * @param trail - trail to be deleted
     * @throws Exception - handle errors
     */
    void delete(Trail trail) throws Exception;
}
