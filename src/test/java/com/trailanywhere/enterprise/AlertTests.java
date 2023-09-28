package com.trailanywhere.enterprise;

import com.trailanywhere.enterprise.dao.IAlertDAO;
import com.trailanywhere.enterprise.dto.Alert;
import com.trailanywhere.enterprise.dto.Trail;
import com.trailanywhere.enterprise.dto.User;
import com.trailanywhere.enterprise.service.AlertServiceStub;
import com.trailanywhere.enterprise.service.IAlertService;
import com.trailanywhere.enterprise.service.ITrailService;
import com.trailanywhere.enterprise.service.IUserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

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
    ArrayList<Trail> trailList = new ArrayList<>();

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
        alertService = new AlertServiceStub(alertDAO);
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
    void createAlertForTrail() {
        givenTrailDataIsAvailable();
        whenUserIsLoggedIn();
        thenCreateAlertForTrail();
    }

    private void givenTrailDataIsAvailable() {

    }

    private void whenUserIsLoggedIn() {
        user.setName("Jacob");
        userService.addUser(user);
        userList = userService.fetchLoggedInUsers();
        assertTrue(userService.loginUser(user));
    }

    private void thenCreateAlertForTrail() {
        // Set test trail data
        Trail testTrail = new Trail();
        testTrail.setName("Forrest Park");
        trailService.addTrail(testTrail);
        trailList = trailService.fetchAlltrails();

        // Set test alert data
        Alert alert = new Alert();
        alert.setUser(user);
        alert.setTrail(testTrail);
        alertService.addAlert(alert);
        ArrayList<Alert> alertList = alertService.fetchAllAlerts();

        // Test conditions
        assertEquals(userList.get(0).getName(), alertList.get(0).getUser().getName());
        assertEquals(trailList.get(0).getName(), alertList.get(0).getTrail().getName());
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
