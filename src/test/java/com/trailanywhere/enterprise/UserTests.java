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
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserDAO userDAO;
    private User user = new User();
    @Autowired
    private ITrailService trailService;
    @Autowired
    private ITrailDAO trailDAO;
    private Trail trail = new Trail();

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
            userService.save(user);
            userService.delete(user);
            User deletedUser = userService.findUser(user.getEmail(), user.getPassword());
            if (user.getEmail().equals(deletedUser.getEmail())) {
                System.out.println("Saved user: " + user);
                System.out.println("Deleted user: " + deletedUser);
                fail("Failed to delete user");
            }
        } catch (Exception e) {
            fail("An error has occurred. " + e);
        }

    }

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
            List<Trail> favorites = userService.fetchFavoriteTrails(user);
            if (favorites.isEmpty()) {
                fail("No favorite trails exist for this user.");
            }
        } catch(Exception e) {
            fail("An error has occurred: " + e);
        }

    }
}
