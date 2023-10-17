package com.trailanywhere.enterprise.service;

import com.trailanywhere.enterprise.dao.IAlertDAO;
import com.trailanywhere.enterprise.dto.Alert;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Contain mock data for IAlertService
 */
@Service
@NoArgsConstructor
public class AlertServiceStub implements IAlertService {
    ArrayList<Alert> allAlerts = new ArrayList<>();
    private IAlertDAO alertDAO;

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
     * @param alert - alert to be deleted
     * @throws Exception - handle errors
     */
    @Override
    public void delete(Alert alert) throws Exception {
        alertDAO.delete(alert);
    }
}
