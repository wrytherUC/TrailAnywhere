package com.trailanywhere.enterprise.service;

import com.trailanywhere.enterprise.dto.User;

import java.util.ArrayList;

public interface IUserService {
    /**
     * Fetch all logged-in users
     * @return - list of users
     */
    ArrayList<User> fetchLoggedInUsers();

    /**
     * login a user
     */
    boolean loginUser(User user);

    /**
     * logout a user
     * @param user - user to be logged out
     */
    void logoutUser(User user);

    /**
     * Add a user
     * @param user - new user
     */
    void addUser(User user);

    /**
     * Create a new user
     * @param user - new user data
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
}
