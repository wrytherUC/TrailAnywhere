package com.trailanywhere.enterprise.dao;

import com.trailanywhere.enterprise.dto.Alert;

import java.util.ArrayList;
import java.util.List;

public interface IAlertDAO {
    /**
     * Create an alert.
     * @param alert - alert object
     * @return - created alert
     */
    Alert save(Alert alert) throws Exception;

    /**
     * Delete an alert
     * @param alertID - alert to be deleted
     * @throws Exception - handle errors
     */
    void delete(int alertID) throws Exception;

    /**
     * Fetch all alerts
     * @return - list of alerts
     */
    List<Alert> fetchAllAlerts();
}
