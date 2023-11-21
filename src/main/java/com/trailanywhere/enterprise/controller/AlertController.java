package com.trailanywhere.enterprise.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.trailanywhere.enterprise.dto.*;
import com.trailanywhere.enterprise.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This controller will handle all endpoints related to alerts.
 */
@Controller
public class AlertController {
    @Autowired
    AlertService alertService;
    @Autowired
    TrailService trailService;
    @Autowired
    UserService userService;
    private static final Logger logger = Logger.getLogger(TrailController.class.getName());

    /**
     * Endpoint to show all alerts
     * @return - alerts JSON
     */
    @GetMapping("/alert")
    @ResponseBody
    public List<Alert> fetchAllAlerts() {
        return alertService.fetchAllAlerts();
    }

    /**
     * Endpoint to show all alerts for a trail
     * @param trailID - trail ID
     * @return - alerts JSON
     */
    @GetMapping("/alert/{trailID}/")
    public ResponseEntity fetchAlertByTrailId (@PathVariable("trailID") int trailID) {
        List<Alert> foundAlerts = alertService.findAlertsForTrail(trailID);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(foundAlerts, headers, HttpStatus.OK);
    }

    /**
     * Show alerts for a trail
     * @param trailID - trail ID
     * @return - view with alert data
     */
    @GetMapping("/alertsByTrailId/{trailID}/")
    public ModelAndView alertsByTrailId (@PathVariable("trailID") int trailID) {
        ModelAndView modelAndView = new ModelAndView();
        Map<Trail, String> trailDetails = new HashMap<>();

        modelAndView.setViewName("trailDetails");
        List<Alert> foundAlerts = alertService.findAlertsForTrail(trailID);
        Trail foundTrail = trailService.findTrailByID(trailID);

        JsonNode node = trailService.getCurrentWeather(foundTrail.getLatitude(), foundTrail.getLongitude());
        trailDetails.put(foundTrail, node.at("/current_weather/temperature").asText());

        modelAndView.addObject("foundAlerts", foundAlerts);
        modelAndView.addObject("trailDetails", trailDetails);
        return modelAndView;
    }

    /**
     * Save an alert
     * @param trailID - trail
     * @param userID - user
     * @param alertText - alert message
     * @return - saved alert
     */
    @PostMapping("/addTrailAlert")
    @ResponseBody
    public Alert addTrailAlert(int trailID, int userID, String alertText) {
        try {
            return alertService.save(trailID, userID, alertText);
        } catch (Exception e) {
            logger.severe("Error adding trail alert: " + e);
            return new Alert();
        }
    }

    /**
     * Populate select box with a user's alerts
     * @param trailID - trail
     * @param userID - user
     * @return - alerts
     */
    @PostMapping("/getUserAlerts")
    @ResponseBody
    public List<LabelValue> getUserAlerts(int trailID, int userID) {
        List<Alert> alerts = alertService.findAlertsForTrail(trailID);
        List<LabelValue> alertData = new ArrayList<>();
        for (Alert alert : alerts) {
            if (alert.getUser().getUserID() == userID) {
                LabelValue labelValue = new LabelValue();
                labelValue.setLabel(alert.getAlertText());
                labelValue.setValue(alert.getAlertID());
                alertData.add(labelValue);
            }
        }
        return alertData;
    }

    /**
     * Endpoint for deleting an alert
     * @param alertID - alert to be deleted
     */
    @PostMapping("/deleteAlert")
    @ResponseBody
    public void deleteAlert(int alertID) {
        try {
            alertService.delete(alertID);
        } catch(Exception e) {
            logger.severe("Error deleting alert: " + e);
        }
    }
}
