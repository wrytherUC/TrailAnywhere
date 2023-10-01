package com.trailanywhere.enterprise.service;

import com.trailanywhere.enterprise.dao.IUserDAO;
import com.trailanywhere.enterprise.dto.Trail;
import com.trailanywhere.enterprise.dto.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Contain mock data for IUserService
 */
@Component
public class UserServiceStub implements IUserService {
    ArrayList<User> users = new ArrayList<>();
    ArrayList<User> loggedInUsers = new ArrayList<>();
    ArrayList<Trail> favoriteTrails = new ArrayList<>();
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
     * Fetch a user's favorite trails
     * @param user - user
     * @return - list of trails
     */
    @Override
    public ArrayList<Trail> fetchFavoriteTrails(User user) {
        ArrayList<Trail> userFavorites = new ArrayList<>();
        for (Trail trail : favoriteTrails) {
            if (Objects.equals(trail.getUser().getName(), user.getName())) {
                userFavorites.add(trail);
            }
        }
        return userFavorites;
    }

    /**
     * Favorite a trail
     * @param trail - trail
     */
    @Override
    public void addFavoriteTrail(User user, Trail trail) {
        trail.setUser(user);
        favoriteTrails.add(trail);
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
     * Logout a user
     * @param user - user to be logged out
     */
    @Override
    public void logoutUser(User user) {
        for (int i = 0; i < loggedInUsers.size(); i++) {
            if (Objects.equals(loggedInUsers.get(i).getName(), user.getName())) {
                loggedInUsers.remove(i);
                break;
            }
        }
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

    /**
     * Delete a user
     * @param user - user to be deleted
     * @throws Exception - handle errors
     */
    @Override
    public void delete(User user) throws Exception {
        userDAO.delete(user);
    }
}