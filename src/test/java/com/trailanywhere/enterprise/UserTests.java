package com.trailanywhere.enterprise;

import com.trailanywhere.enterprise.dao.ITrailDAO;
import com.trailanywhere.enterprise.dao.IUserDAO;
import com.trailanywhere.enterprise.dto.Trail;
import com.trailanywhere.enterprise.dto.User;
import com.trailanywhere.enterprise.service.ITrailService;
import com.trailanywhere.enterprise.service.IUserService;
import com.trailanywhere.enterprise.service.TrailService;
import com.trailanywhere.enterprise.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeastOnce;

@SpringBootTest
public class UserTests {
    private IUserService userService;
    private final IUserDAO userDAO;
    private User user;
    private ITrailService trailService;
    private final ITrailDAO trailDAO;
    private Trail trail;

    @Autowired
    public UserTests(IUserService userService, IUserDAO userDAO, ITrailService trailService, ITrailDAO trailDAO) {
        this.userService = userService;
        this.userDAO = userDAO;
        this.trailService = trailService;
        this.trailDAO = trailDAO;
        this.user = new User();
        this.trail = new Trail();
    }

    /**
     * Test creating a new user
     * @throws Exception - handle errors
     */
    @Test
    void createUser() throws Exception {
        givenUserDataIsAvailable();
        whenUserDataIsCreated();
        thenCreateNewUser();
    }

    private void givenUserDataIsAvailable() throws Exception {
        userService = new UserService(userDAO);
    }

    private void whenUserDataIsCreated() {
        user.setName("Sam");
        user.setEmail("sam@gmail.com");
        user.setPassword("password");
    }

    private void thenCreateNewUser() throws Exception {
        User newUser = userService.save(user);
        assertEquals(user, newUser);
    }

    /**
     * Test logging out a user
     */
    @Test
    void logoutUser() throws Exception {
        givenUserDataIsAvailable();
        whenUserDataIsCreated();
        thenLogoutUser();
    }

    private void thenLogoutUser() {
        // Add user to the logged in list
        userService.addUser(user);
        userService.logoutUser(user);
        ArrayList<User> userList;
        userList = userService.fetchLoggedInUsers();
        for (User value : userList) {
            if (Objects.equals(value.getName(), user.getName())) {
                fail("User has not been logged out");
            }
        }
    }

    /**
     * Test deleting a user
     * @throws Exception - handle errors
     */
    @Test
    void deleteUser() throws Exception {
        givenUserDataIsAvailable();
        whenUserDataIsCreated();
        thenDeleteUser();
    }

    private void thenDeleteUser() {
        try {
            user.setEmail("sam1@gmail.com");
            User userDeleted = userService.save(user);
            userService.delete(userDeleted.getUserID());
            User deletedUser = userService.findUser(user.getEmail(), user.getPassword());
            if (user.getEmail().equals(deletedUser.getEmail())) {
                fail("Failed to delete user");
            }
        } catch (Exception e) {
            fail("An error has occurred. " + e);
        }

    }

    /**
     * Test adding and fetching favorite trails
     * @throws Exception - handle errors
     */
    @Test
    void addingAFavoriteTrailForAUser() throws Exception {
        givenUserDataIsAvailable();
        givenTrailDataIsAvailable();
        whenUserDataIsCreated();
        whenTrailDataIsCreated();
        thenAddTrailToFavoritesList();
    }

    private void givenTrailDataIsAvailable() {
        trailService = new TrailService(trailDAO);
    }

    private void whenTrailDataIsCreated() {
        trail.setName("Ridge Park");
    }

    private void thenAddTrailToFavoritesList() {
        try {
            user.setEmail("sam2@gmail.com");
            userService.addFavoriteTrail(user, trail);
            List<Trail> favorites = userService.fetchFavoriteTrails(user.getUserID());
            if (favorites.isEmpty()) {
                fail("No favorite trails exist for this user.");
            }
        } catch(Exception e) {
            fail("An error has occurred: " + e);
        }

    }

    /**
     * Test deleting a favorite trail
     * @throws Exception - handle errors
     */
    @Test
    void deleteFavoriteTrails() throws Exception {
        givenUserDataIsAvailable();
        givenTrailDataIsAvailable();
        whenUserDataIsCreated();
        whenTrailDataIsCreated();
        thenDeleteFavoriteTrail();
    }

    private void thenDeleteFavoriteTrail() {
        user.setEmail("samDelete@gmail.com");
        trail.setName("Deleted Park");
        userService.addFavoriteTrail(user, trail);
        userService.deleteFavoriteTrail(user.getUserID(), trail.getTrailID());
        List<Trail> favorites = userService.fetchFavoriteTrails(user.getUserID());
        if (!favorites.isEmpty()) {
            fail("Failed to delete favorite trail");
        }
    }

    /**
     * Fetch a user by their ID
     * @throws Exception - handle errors
     */
    @Test
    void fetchUserByID() throws Exception {
        givenUserDataIsAvailable();
        whenUserDataIsCreated();
        thenFetchUserByID();
    }

    private void thenFetchUserByID() {
        try {
            user.setEmail("deletedUser@gmail.com");
            userService.save(user);
            int userID = user.getUserID();
            User fetchedUser = userService.findUserByID(userID);
            assertEquals(userID, fetchedUser.getUserID());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
