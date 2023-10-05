package com.trailanywhere.enterprise.service;

import com.trailanywhere.enterprise.dto.Alert;

import java.util.ArrayList;

public interface IAlertService {
    /**
     * Fetch all alerts
     * @return - list of alerts
     */
    ArrayList<Alert> fetchAllAlerts();

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
     * @param alert - alert to be deleted
     * @throws Exception - handle errors
     */
    void delete(Alert alert) throws Exception;
}
