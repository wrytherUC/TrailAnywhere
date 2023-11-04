package com.trailanywhere.enterprise.service;

import com.trailanywhere.enterprise.dto.Alert;

import java.util.ArrayList;
import java.util.List;

public interface IAlertService {
    /**
     * Fetch all alerts
     * @return - list of alerts
     */
    List<Alert> fetchAllAlerts();

    /**
     * Add an alert
     * @param alert - new alert
     */
    void addAlert(Alert alert);

    /**
     * Create a new alert
     * @param alert - alert data
     * @return - new alert
     * @throws Exception - handle errors
     */
    Alert save(Alert alert) throws Exception;

    /**
     * Delete an alert
     * @param alertID - alert to be deleted
     * @throws Exception - handle errors
     */
    void delete(int alertID) throws Exception;

    /**
     * Find alert based on its ID
     * @param alertID - alert ID
     * @return - alert
     */
    Alert findAlertByID(int alertID);

    /**
     * Find alerts related to a trail
     * @param trailID - trail ID
     * @return - alerts
     */
    List<Alert> findAlertsForTrail(int trailID);

    /**
     * Find alerts made by a user
     * @param userID - user
     * @return - alerts
     */
    List<Alert> findAlertsForUser(int userID);
}
