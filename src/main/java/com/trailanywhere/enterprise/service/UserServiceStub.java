package com.trailanywhere.enterprise.service;

import com.trailanywhere.enterprise.dao.IUserDAO;
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
    private IUserDAO userDAO;

    /**
     * Default constructor
     */
    public UserServiceStub() {}

    /**
     * Constructor using DAO
     * @param userDAO - Handle CRUD operations
     */
    public UserServiceStub(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Fetch all logged-in users
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

    /**
     * Save a user
     * @param user - new user object
     * @return - newly created user
     * @throws Exception - handle errors
     */
    @Override
    public User save(User user) throws Exception {
        return userDAO.save(user);
    }
}
