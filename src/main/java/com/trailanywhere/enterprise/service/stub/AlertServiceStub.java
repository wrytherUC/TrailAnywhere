package com.trailanywhere.enterprise.service.stub;

import com.trailanywhere.enterprise.dao.IAlertDAO;
import com.trailanywhere.enterprise.dto.Alert;
import com.trailanywhere.enterprise.dto.Trail;
import com.trailanywhere.enterprise.dto.User;
import com.trailanywhere.enterprise.service.TrailService;
import com.trailanywhere.enterprise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.trailanywhere.enterprise.service.IAlertService;

import java.util.ArrayList;
import java.util.List;

/**
 * Contain mock data for IAlertService
 */
@Component
public class AlertServiceStub implements IAlertService {
    ArrayList<Alert> allAlerts = new ArrayList<>();
    private IAlertDAO alertDAO;

    @Autowired
    TrailService trailService;
    @Autowired
    UserService userService;

    /**
     * Default constructor
     */
    public AlertServiceStub() {

    }

    /**
     * Constructor using DAO
     * @param alertDAO - DAO
     */
    public AlertServiceStub(IAlertDAO alertDAO) {
        this.alertDAO = alertDAO;
    }

    /**
     * Fetch all alerts
     * @return - list of alerts
     */
    @Override
    public ArrayList<Alert> fetchAllAlerts() {
        return allAlerts;
    }

    /**
     * Add an alert
     * @param alert - new alert
     */
    @Override
    public void addAlert(Alert alert) {
        allAlerts.add(alert);
    }

    /**
     * Create a new alert
     * @param trailID - trail ID to associate the alert with
     * @param userID - user ID to associate the alert with
     * @param alertText - Describes what the alert is
     * @return - alert
     * @throws Exception - handle errors
     */
    @Override
    public Alert save(int trailID, int userID, String alertText) throws Exception {

        Alert alert = new Alert();

        alert.setAlertText(alertText);

        Trail trail = trailService.findTrailByID(trailID);
        alert.setTrail(trail);

        User user = userService.findUserByID(userID);
        alert.setUser(user);

        return alertDAO.save(alert);
    }

    /**
     * Delete an alert
     * @param alertID - alert to be deleted
     * @throws Exception - handle errors
     */
    @Override
    public void delete(int alertID) throws Exception {
        for (int i = 0; i < allAlerts.size(); i++) {
            if (allAlerts.get(i).getAlertID() == alertID) {
                allAlerts.remove(i);
            }
        }
    }

    /**
     * Find an alert
     * @param alertID - alert ID
     * @return - found alert
     */
    @Override
    public Alert findAlertByID(int alertID) {
        for (Alert alert : allAlerts) {
            if (alert.getAlertID() == alertID) {
                return alert;
            }
        }
        return new Alert();
    }

    /**
     * Get all alerts for a trail
     * @param trailID - trail ID
     * @return - list of alerts
     */
    @Override
    public List<Alert> findAlertsForTrail(int trailID) {
        List<Alert> foundAlerts = new ArrayList<>();
        for (Alert alert : allAlerts) {
            if (alert.getTrail().getTrailID() == trailID) {
                foundAlerts.add(alert);
            }
        }
        return foundAlerts;
    }

    /**
     * Find all alerts for a user
     * @param userID - user
     * @return - list of alerts
     */
    @Override
    public List<Alert> findAlertsForUser(int userID) {
        List<Alert> foundAlerts = new ArrayList<>();
        for (Alert alert : allAlerts) {
            if (alert.getUser().getUserID() == userID) {
                foundAlerts.add(alert);
            }
        }
        return foundAlerts;
    }
}