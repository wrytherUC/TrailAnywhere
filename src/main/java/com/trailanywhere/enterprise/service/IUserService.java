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
     * Add a user
     * @param user - new user
     */
    void addUser(User user);
}
