package com.trailanywhere.enterprise.service;

import com.trailanywhere.enterprise.dao.IAlertDAO;
import com.trailanywhere.enterprise.dto.Alert;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Contain mock data for IAlertService
 */
@Service
@NoArgsConstructor
public class AlertService implements IAlertService {
    ArrayList<Alert> allAlerts = new ArrayList<>();
    @Autowired
    private IAlertDAO alertDAO;

    /**
     * Constructor using DAO
     * @param alertDAO - DAO
     */
    public AlertService(IAlertDAO alertDAO) {
        this.alertDAO = alertDAO;
    }

    /**
     * Fetch all alerts
     * @return - list of alerts
     */
    @Override
    public List<Alert> fetchAllAlerts() {
        return alertDAO.fetchAllAlerts();
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
     * @param alert - alert data
     * @return - alert
     * @throws Exception - handle errors
     */
    @Override
    public Alert save(Alert alert) throws Exception {
        return alertDAO.save(alert);
    }

    /**
     * Delete an alert
     * @param alertID - alert to be deleted
     * @throws Exception - handle errors
     */
    @Override
    public void delete(int alertID) throws Exception {
        alertDAO.delete(alertID);
    }

    /**
     * Find alert based on its ID
     * @param alertID - alert ID
     * @return - alert
     */
    @Override
    public Alert findAlertByID(int alertID) {
        return alertDAO.findAlertByID(alertID);
    }
}
