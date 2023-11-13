package com.trailanywhere.enterprise.dao;

import com.trailanywhere.enterprise.dto.Trail;
import com.trailanywhere.enterprise.dto.User;

import java.util.List;

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
     * @param userID - user to be deleted
     * @throws Exception - handle errors
     */
    void delete(int userID) throws Exception;

    /**
     * Fetch a user's favorite trails
     *
     * @return - list of trails
     */
    List<Trail> fetchFavoriteTrails(int userID);

    /**
     * Add a favorite trail
     * @param user - user who's adding the trail
     * @param trail - trail to be added
     */
    void addFavoriteTrail(User user, Trail trail);

    /**
     * Remove a favorite trail
     * @param userID - User
     * @param trailID - Trail to be unfavorited
     */
    void deleteFavoriteTrail(int userID, int trailID);

    /**
     * Find a user based on their credentials
     * @param email - email address
     * @param password - user password
     */
    User findUser(String email, String password);

    /**
     * Find a user based on their ID
     * @param userID - user ID
     * @return - user
     */
    User findUserByID(int userID);

    User findUserByName(String name);

    /**
     * Check if an email already exists
     * @param email - email
     * @return - user
     */
    User findExistingEmail(String email);
}
