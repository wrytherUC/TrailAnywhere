package com.trailanywhere.enterprise.controller;

import com.trailanywhere.enterprise.dto.Alert;
import com.fasterxml.jackson.databind.JsonNode;
import com.trailanywhere.enterprise.dto.LabelValue;
import com.trailanywhere.enterprise.dto.Trail;
import com.trailanywhere.enterprise.service.IAlertService;
import com.trailanywhere.enterprise.service.ITrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.logging.Level;
import org.springframework.ui.Model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


/**
 * This controller will handle all Trail endpoints.
 */
@Controller
public class TrailController {

    @Autowired
    ITrailService trailService;

    @Autowired
    IAlertService alertService;

    private static final Logger logger = Logger.getLogger(TrailController.class.getName());

    /**
     * Handle the root endpoint and return the homepage.
     * @return homepage
     */
    @RequestMapping("/")
    public String index(Model model) {
        List<Trail> allTrails = trailService.fetchAllTrails();
        Map<Trail, String> trailData = new HashMap<>();
        for (Trail trail : allTrails) {
            JsonNode node = trailService.getCurrentWeather(trail.getLatitude(), trail.getLongitude());
            trailData.put(trail, node.at("/current_weather/temperature").asText());
        }
        model.addAttribute("trailData", trailData);
        return "TrailFinder";
    }

    /**
     * API POST endpoint for creating trails
     * @param trail the trail to be created
     * @return created trail in JSON format
     */
    @PostMapping(value="/trail", consumes="application/json", produces="application/json")
    @ResponseBody
    public ResponseEntity createTrail(@RequestBody Trail trail) {
        Trail newTrail;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            newTrail = trailService.save(trail);
        } catch (Exception e) {
            logger.severe("Error creating Trail: " + e.getMessage());
            return new ResponseEntity(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(newTrail, headers, HttpStatus.OK);
    }

    @GetMapping("/trail")
    @ResponseBody
    public List<Trail> fetchAllTrails() {
        return trailService.fetchAllTrails();
    }


    @GetMapping("/trail/{name}/")
    public ResponseEntity fetchTrailByName (@PathVariable("name") String name) {
        Trail foundTrail = trailService.fetchByTrailName(name);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(foundTrail, headers, HttpStatus.OK);
    }

    @DeleteMapping("/trail/{name}/")
    public ResponseEntity deleteTrail(@PathVariable("name") int trailID) {
        logger.log(Level.INFO,"Entering delete trail endpoint" );
        try {
            trailService.delete(trailID);
            logger.log(Level.INFO,"Trail with ID " + trailID + " was deleted." );
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Unable to delete trail with name: " + trailID + ". Message: " + e.getMessage(), e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/alert")
    @ResponseBody
    public List<Alert> fetchAllAlerts() {
        return alertService.fetchAllAlerts();
    }

    //Need input on this
//    @GetMapping("/alert/{name}/")
//    public ResponseEntity fetchAlertByTrailId (@PathVariable("name") String name) {
//        Alert foundAlert = alertService.fetchAllAlerts();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        return new ResponseEntity(foundAlert, headers, HttpStatus.OK);
//    }


    /**
     * Handle the alerts endpoint and return a page.
     * @return alerts page
     */
    @RequestMapping("/Alerts")
    public String alerts() {
        return "Alerts";
    }

    /**
     * Handle the favorites endpoint and return a page.
     * @return favorites page
     */
    @RequestMapping("/Favorites")
    public String favorites() {
        return "Favorites";
    }

    /**
     * Handle the login endpoint and return a page.
     * @return login page
     */
    @RequestMapping("/Login")
    public String login() {
        return "Login";
    }

    /**
     * Handle the create endpoint and return a page.
     * @return create page
     */
    @RequestMapping("/CreateTrail")
    public String create() {
        return "CreateTrail";
    }

    @GetMapping("/trailAutocomplete")
    @ResponseBody
    public List<LabelValue> trailAutocomplete(@RequestParam(value="term", required = false, defaultValue="") String term) {
        List<Trail> allTrails = trailService.fetchAllTrails();
        List<LabelValue> trailData = new ArrayList<>();
        for (Trail trail : allTrails) {
            LabelValue labelValue = new LabelValue();

            if (trail.getName().toLowerCase().contains(term.toLowerCase())) {
                labelValue.setLabel(trail.getName());
                labelValue.setValue(trail.getTrailID());
                trailData.add(labelValue);
            } else if (trail.getTrailType().toLowerCase().contains(term.toLowerCase())) {
                labelValue.setLabel(trail.getTrailType());
                labelValue.setValue(trail.getTrailID());
                trailData.add(labelValue);
                break;
            } else if (trail.getDifficulty().toLowerCase().contains(term.toLowerCase())) {
                labelValue.setLabel(trail.getDifficulty());
                labelValue.setValue(trail.getTrailID());
                trailData.add(labelValue);
                break;
            } else if(trail.getZipCode().toLowerCase().contains(term.toLowerCase())) {
                labelValue.setLabel(trail.getZipCode());
                labelValue.setValue(trail.getTrailID());
                trailData.add(labelValue);
            }
        }
        return trailData;
    }

    /**
     * getTrailJSON endpoint returns all current trails in JSON format
     *
     * @return - JSON of all trails
     */
    @RequestMapping(value="/getTrailJSON", method=RequestMethod.GET)
    @ResponseBody
    public List<Trail> getTrailJSON() {
        return trailService.fetchAllTrails();
    }

}
