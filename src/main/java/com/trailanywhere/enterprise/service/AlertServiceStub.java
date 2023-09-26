package com.trailanywhere.enterprise.service;

import com.trailanywhere.enterprise.dto.Alert;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Contain mock data for IAlertService
 */
@Component
public class AlertServiceStub implements IAlertService {
    ArrayList<Alert> allAlerts = new ArrayList<>();

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
}
