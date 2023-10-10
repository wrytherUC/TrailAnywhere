package com.trailanywhere.enterprise.dao;

import com.trailanywhere.enterprise.dto.Trail;
import com.trailanywhere.enterprise.dto.User;

import java.util.ArrayList;

/**
 * Interface for CRUD operations related to users
 */
public interface IUserDAO {
    /**
     * Save a user
     * @param user - user object
     * @return - new user
     * @throws Exception - handle errors
     */
    User save(User user) throws Exception;

    /**
     * Delete a user
     * @param user - user to be deleted
     * @throws Exception - handle errors
     */
    void delete(User user) throws Exception;

    /**
     * Fetch a user's favorite trails
     * @return - list of trails
     */
    ArrayList<Trail> fetchFavoriteTrails(User user);

    /**
     * Add a favorite trail
     * @param user - user who's adding the trail
     * @param trail - trail to be added
     */
    void addFavoriteTrail(User user, Trail trail);
}
