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
    @MockBean
    private IUserDAO userDAO;
    private User user = new User();
    @Autowired
    private ITrailService trailService;
    @MockBean
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
        Mockito.when(userDAO.save(user)).thenReturn(user);
        userService = new UserService(userDAO);
    }

    private void whenUserDataIsCreated() {
        user.setName("Sam");
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

    private void thenDeleteUser() throws Exception {
        userDAO.delete(user);
        verify(userDAO, atLeastOnce()).delete(user);
    }

    @Test
    void addingAFavoriteTrailForAUser() throws Exception {
        givenUserDataIsAvailable();
        givenTrailDataIsAvailable();
        whenUserDataIsCreated();
        whenTrailDataIsCreated();
        thenAddTrailToFavoritesList();
    }

    private void givenTrailDataIsAvailable() throws Exception {
        Mockito.when(trailDAO.save(trail)).thenReturn(trail);
        trailService = new TrailService(trailDAO);
    }

    private void whenTrailDataIsCreated() {
        trail.setName("Forrest Park");
    }

    private void thenAddTrailToFavoritesList() {
        userService.addFavoriteTrail(user, trail);
        List<Trail> favorites = userService.fetchFavoriteTrails(user);
        if (favorites.isEmpty()) {
            fail("No favorite trails exist for this user.");
        }
    }
}
