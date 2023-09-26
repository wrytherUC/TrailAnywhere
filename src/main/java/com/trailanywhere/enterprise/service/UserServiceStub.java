package com.trailanywhere.enterprise.service;

import com.trailanywhere.enterprise.dto.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Contain mock data for IUserService
 */
@Component
public class UserServiceStub implements IUserService {
    ArrayList<User> users = new ArrayList<>();
    ArrayList<User> loggedInUsers = new ArrayList<>();

    /**
     * Fetch all logged in users
     * @return all logged in users
     */
    @Override
    public ArrayList<User> fetchLoggedInUsers() {
        return loggedInUsers;
    }

    /**
     * Log-in a user
     * @param user - user
     * @return login status
     */
    @Override
    public boolean loginUser(User user) {
        return true;
    }

    /**
     * Add a user to the userList and loggedInUserList
     * @param user - new user
     */
    @Override
    public void addUser(User user) {
        users.add(user);
        loggedInUsers.add(user);
    }
}
