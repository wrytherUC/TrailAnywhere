package com.trailanywhere.enterprise;

import com.trailanywhere.enterprise.dao.IAlertDAO;
import com.trailanywhere.enterprise.dao.ITrailDAO;
import com.trailanywhere.enterprise.dto.Alert;
import com.trailanywhere.enterprise.dto.Trail;
import com.trailanywhere.enterprise.dto.User;
import com.trailanywhere.enterprise.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeastOnce;

/**
 * Handle all tests related to alerts
 */
@SpringBootTest
public class AlertTests {
    @Autowired
    private IAlertService alertService;
    @MockBean
    private IAlertDAO alertDAO;
    private Alert alert = new Alert();
    private User user = new User();
    @Autowired
    private IUserService userService;
    ArrayList<User> userList = new ArrayList<>();
    @Autowired
    private ITrailService trailService;
    @MockBean
    private ITrailDAO trailDAO;
    private Trail trail = new Trail();
    private List<Trail> trailList;
    //List<Trail> trailList = new ArrayList<>();

    /**
     * Test creating a new alert
     * @throws Exception - handle errors
     */
    @Test
    void saveAlert() throws Exception {
        givenAlertDataIsAvailable();
        whenAlertDataIsGiven();
        thenCreateAlert();
    }

    private void givenAlertDataIsAvailable() throws Exception {
        Mockito.when(alertDAO.save(alert)).thenReturn(alert);
        alertService = new AlertService(alertDAO);
    }

    private void whenAlertDataIsGiven() {
        User user = new User();
        user.setName("Jacob");
        Trail trail = new Trail();
        trail.setName("Forrest Park");
        alert.setUser(user);
        alert.setTrail(trail);
    }

    private void thenCreateAlert() throws Exception {
        Alert newAlert = alertService.save(alert);
        assertEquals(alert, newAlert);
    }

    /**
     * Test for creating an alert with a provided trail name (user must be logged in).
     */
    @Test
    void createAlertForTrail() throws Exception {
        givenTrailDataIsAvailable();
        whenUserIsLoggedIn();
        thenCreateAlertForTrail();
    }

    private void givenTrailDataIsAvailable() throws Exception {
        Mockito.when(trailDAO.save(trail)).thenReturn(trail);
        trailService = new TrailService(trailDAO);
    }

    private void whenUserIsLoggedIn() {
        user.setName("Jacob");
        userService.addUser(user);
        userList = userService.fetchLoggedInUsers();
        assertTrue(userService.loginUser(user));
    }

    private void thenCreateAlertForTrail() throws Exception {
        // Set test trail data
        trail = trailService.fetchByTrailName("Forrest Park");

        // Set test alert data
        Alert alert = new Alert();
        alert.setUser(user);
        alert.setTrail(trail);
        alertService.addAlert(alert);
        ArrayList<Alert> alertList = alertService.fetchAllAlerts();

        // Test conditions
        assertEquals(userList.get(0).getName(), alertList.get(0).getUser().getName());
        //assertEquals(trailList.get(0).getName(), alertList.get(0).getTrail().getName());
    }

    /**
     * Delete an alert
     * @throws Exception - handle errors
     */
    @Test
    void deleteAlert() throws Exception {
        givenAlertDataIsAvailable();
        whenAlertDataIsGiven();
        thenDeleteAlert();
    }

    private void thenDeleteAlert() throws Exception {
        alertService.delete(alert);
        verify(alertDAO, atLeastOnce()).delete(alert);
    }

}
