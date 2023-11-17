package com.trailanywhere.enterprise.service.stub;

import com.trailanywhere.enterprise.dao.IUserDAO;
import com.trailanywhere.enterprise.dto.Trail;
import com.trailanywhere.enterprise.dto.User;
import com.trailanywhere.enterprise.dto.UserFavoriteTrails;
import org.springframework.stereotype.Component;
import com.trailanywhere.enterprise.service.IUserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Contain mock data for IUserService
 */
@Component
public class UserServiceStub implements IUserService {
    ArrayList<User> users = new ArrayList<>();
    ArrayList<User> loggedInUsers = new ArrayList<>();
    List<UserFavoriteTrails> favoriteTrails = new ArrayList<>();
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
     * Find a user's favorite trails
     * @param userID - user
     * @return - list of trails
     */
    @Override
    public List<Trail> fetchFavoriteTrails(int userID) {
        List<Trail> foundTrails = new ArrayList<>();
        for (UserFavoriteTrails favoriteTrail : favoriteTrails) {
            if (favoriteTrail.getUser().getUserID() == userID) {
                foundTrails.add(favoriteTrail.getTrail());
            }
        }
        return foundTrails;
    }

    /**
     * Add a favorite trail
     * @param user - user who's adding the trail
     * @param trail - trail to be added
     */
    @Override
    public void addFavoriteTrail(User user, Trail trail) {
        UserFavoriteTrails favoriteTrail = new UserFavoriteTrails();
        favoriteTrail.setTrail(trail);
        favoriteTrail.setUser(user);
        favoriteTrails.add(favoriteTrail);
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
            if (loggedInUsers.get(i).equals(user)) {
                loggedInUsers.remove(i);
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
        users.add(user);
        return user;
    }

    /**
     * Delete a user
     * @param userID - user to be deleted
     * @throws Exception - handle errors
     */
    @Override
    public void delete(int userID) throws Exception {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserID() == userID) {
                users.remove(i);
            }
        }
    }

    /**
     * Find a user
     * @param email - email address
     * @param password - user password
     * @return - found user
     */
    @Override
    public User findUser(String email, String password) {
        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return new User();
    }

    /**
     * Delete a favorite trail
     * @param userID - User
     * @param trailID - Trail to be unfavorited
     */
    @Override
    public void deleteFavoriteTrail(int userID, int trailID) {
         for (int i = 0; i < favoriteTrails.size(); i++) {
             if (favoriteTrails.get(i).getUser().getUserID() == userID && favoriteTrails.get(i).getTrail().getTrailID() == trailID) {
                 favoriteTrails.remove(i);
             }
         }
    }

    /**
     * Find a user by their ID
     * @param userID - user ID
     * @return - found user
     */
    @Override
    public User findUserByID(int userID) {
        for (User user : users) {
            if (user.getUserID() == userID) {
                return user;
            }
        }
        return new User();
    }

    /**
     * Find a user by their name
     * @param name - user's name
     * @return - found user
     */
    @Override
    public User findUserByName(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return new User();
    }

    /**
     * Find if an email already exists
     * @param email - email
     * @return - found user
     */
    @Override
    public User findExistingEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return new User();
    }
}