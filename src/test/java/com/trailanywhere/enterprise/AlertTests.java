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

import static org.assertj.core.api.Fail.fail;
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
    @Autowired
    private IAlertDAO alertDAO;
    private Alert alert = new Alert();
    private User user = new User();
    @Autowired
    private IUserService userService;
    ArrayList<User> userList = new ArrayList<>();
    @Autowired
    private ITrailService trailService;
    @Autowired
    private ITrailDAO trailDAO;
    private Trail trail = new Trail();

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

    private void givenAlertDataIsAvailable() {
        alertService = new AlertService(alertDAO);
    }

    private void whenAlertDataIsGiven() {
        User user = new User();
        user.setName("Jacob");
        Trail trail = new Trail();
        trail.setName("Rapid Run Park");
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

    private void givenTrailDataIsAvailable() {
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
        trail = trailService.fetchByTrailName("French Park");

        // Set test alert data
        Alert alert = new Alert();
        alert.setUser(user);
        alert.setTrail(trail);
        alertService.addAlert(alert);
        alertService.save(alert);
        List<Alert> alertList = alertService.fetchAllAlerts();

        // Test conditions
        assertEquals(userList.get(0).getName(), alertList.get(0).getUser().getName());
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
        alert.getTrail().setName("New Trail"); // Avoid unique constraint error
        Alert alertDelete = alertService.save(alert);
        alertService.delete(alertDelete.getAlertID());
        List<Alert> alertList = alertService.fetchAllAlerts();
        for (Alert a : alertList) {
            if (a.equals(alert)) {
                fail("Failed to delete alert.");
            }
        }
    }

    /**
     * Test finding an alert by its ID
     */
    @Test
    void findAlertByID() {
        givenAlertDataIsAvailable();
        givenTrailDataIsAvailable();
        whenAlertDataIsGiven();
        thenFindAlertByID();
    }

    private void thenFindAlertByID() {
        try {
            alert.getTrail().setName("findByID");
            alertService.save(alert);
            int alertID = alert.getAlertID();
            Alert foundAlert = alertService.findAlertByID(alertID);
            assertEquals(alertID, foundAlert.getAlertID());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
