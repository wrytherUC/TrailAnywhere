package com.trailanywhere.enterprise.service;

import com.trailanywhere.enterprise.dao.IUserDAO;
import com.trailanywhere.enterprise.dto.Trail;
import com.trailanywhere.enterprise.dto.User;
import com.trailanywhere.enterprise.dto.UserFavoriteTrails;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Contain mock data for IUserService
 */
@Service
@NoArgsConstructor
public class UserService implements IUserService {
    ArrayList<User> users = new ArrayList<>();
    ArrayList<User> loggedInUsers = new ArrayList<>();
    private IUserDAO userDAO;

    /**
     * Constructor using DAO
     * @param userDAO - Handle CRUD operations
     */
    public UserService(IUserDAO userDAO) {
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
     *
     * @param userID - user
     * @return - list of trails
     */
    @Override
    public List<Trail> fetchFavoriteTrails(int userID) {
        return userDAO.fetchFavoriteTrails(userID);
    }

    /**
     * Favorite a trail
     * @param trail - trail
     */
    @Override
    public void addFavoriteTrail(User user, Trail trail) {
        userDAO.addFavoriteTrail(user, trail);
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
     * @param userID - user to be deleted
     * @throws Exception - handle errors
     */
    @Override
    public void delete(int userID) throws Exception {
        userDAO.delete(userID);
    }

    /**
     * Find a user based on their credentials
     * @param email - email address
     * @param password - user password
     * @return - user
     */
    @Override
    public User findUser(String email, String password) {
        return userDAO.findUser(email, password);
    }
}
