package com.trailanywhere.enterprise.controller;

import com.trailanywhere.enterprise.dto.Alert;
import com.fasterxml.jackson.databind.JsonNode;
import com.trailanywhere.enterprise.dto.LabelValue;
import com.trailanywhere.enterprise.dto.Trail;
import com.trailanywhere.enterprise.dto.User;
import com.trailanywhere.enterprise.service.IAlertService;
import com.trailanywhere.enterprise.service.ITrailService;
import com.trailanywhere.enterprise.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Level;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
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

    @Autowired
    IUserService userService;

    private static final Logger logger = Logger.getLogger(TrailController.class.getName());

    /**
     * Handle the root endpoint and return the homepage.
     * @return homepage
     */
    @RequestMapping("/")
    public String index(Model model) {
        return "TrailFinder";
    }

    /**
     * API POST endpoint for creating trails
     * @param trail the trail to be created
     * @return created trail in JSON format
     */
    @PostMapping(value="/trail", produces="application/json")
    @ResponseBody
    public Trail createTrail(Trail trail) {
        Trail foundTrail = trailService.fetchByTrailName(trail.getName());
        try {
            if (foundTrail.getTrailID() == 0) {
                trailService.save(trail);
            }
        } catch (Exception e) {
            logger.severe("Error creating Trail: " + e.getMessage());
            foundTrail = new Trail();
        }
        return foundTrail;
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

    @DeleteMapping("/trail/{trailID}/")
    public ResponseEntity deleteTrail(@PathVariable("trailID") int trailID) {
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

    @RequestMapping("/searchType")
    public String searchType(@RequestParam(value="searchType", required=false, defaultValue="None")  String searchType, Model model) {

        if (searchType.toLowerCase().contains("name")) {
            return "TrailFinder-Name";
        } else if (searchType.toLowerCase().contains("type")) {
            Set<String> allTrailTypes;

            allTrailTypes = trailService.fetchAllTrailTypes();

            model.addAttribute("allTrailTypes", allTrailTypes);

            return "TrailFinder-Type";
        } else if (searchType.toLowerCase().contains("difficulty")) {
            Set<String> allTrailDifficulties;

            allTrailDifficulties = trailService.fetchAllDifficultyTypes();

            model.addAttribute("allTrailDifficulties", allTrailDifficulties);

            return "TrailFinder-Difficulty";
        } else if(searchType.toLowerCase().contains("zipcode") || searchType.toLowerCase().contains("zip code")) {
            return "TrailFinder-ZipCode";
        }
        else {
            return "TrailFinder";
        }
    }

    @GetMapping("/trails")
    public String searchTrailsForm(@RequestParam(value="searchTerm", required=false, defaultValue="None")  String searchTerm, Model model) {
        searchTerm = searchTerm.trim();

        List<Trail> allTrails = trailService.fetchAllTrails();
        List<Trail> trails = null;
        for (Trail trail : allTrails) {
            if (trail.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                trails = Collections.singletonList(trailService.fetchByTrailName(searchTerm));
            } else if (trail.getTrailType().toLowerCase().contains(searchTerm.toLowerCase())) {
                trails = trailService.fetchByTrailType(searchTerm);
                break;
            } else if (trail.getDifficulty().toLowerCase().contains(searchTerm.toLowerCase())) {
                trails = trailService.fetchByDifficulty(searchTerm);
                break;
            } else if (trail.getZipCode().toLowerCase().contains(searchTerm.toLowerCase())) {
                trails = trailService.fetchByZipCode(searchTerm);
            }
        }

        model.addAttribute("trails", trails);
        return "trails";

    }

    @GetMapping("/trailsByDifficulty")
    public String trailsByDifficulty(@RequestParam(value="searchTerm", required=false, defaultValue="None")  String searchTerm, Model model) {

        searchTerm = searchTerm.trim();
        List<Trail> trails;

        trails = trailService.fetchByDifficulty(searchTerm);

        model.addAttribute("trails", trails);

        return "trails";

    }

    @GetMapping("/trailsByName")
    public String trailsByName(@RequestParam(value="searchTerm", required=false, defaultValue="None")  String searchTerm, Model model) {

        searchTerm = searchTerm.trim();
        Trail trail;

        trail = trailService.fetchByTrailName(searchTerm);

        model.addAttribute("trails", trail);
        return "trails";

    }

    @GetMapping("/trailsByType")
    public String trailsByType(@RequestParam(value="searchTerm", required=false, defaultValue="None")  String searchTerm, Model model) {

        searchTerm = searchTerm.trim();
        List<Trail> trails;

        trails = trailService.fetchByTrailType(searchTerm);

        model.addAttribute("trails", trails);
        return "trails";

    }

    @GetMapping("/trailsByZipCode")
    public String trailsByZipCode(@RequestParam(value="searchTerm", required=false, defaultValue="None")  String searchTerm, Model model) {

        searchTerm = searchTerm.trim();
        List<Trail> trails;

        trails = trailService.fetchByZipCode(searchTerm);

        model.addAttribute("trails", trails);
        return "trails";

    }


    @GetMapping("/alert")
    @ResponseBody
    public List<Alert> fetchAllAlerts() {
        return alertService.fetchAllAlerts();
    }

    @GetMapping("/alert/{trailID}/")
    public ResponseEntity fetchAlertByTrailId (@PathVariable("trailID") int trailID) {
        List<Alert> foundAlerts = alertService.findAlertsForTrail(trailID);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(foundAlerts, headers, HttpStatus.OK);
    }

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
     * Handle the favorites endpoint and return a page.
     * @return favorites page
     */
    @RequestMapping("/Favorites")
    public String favorites() {
        return "Favorites";
    }

    /**
     * Get a user's favorite trails
     * @param userID - user ID passed in from the client
     * @return - list of favorite trails
     */
    @PostMapping("/getFavoriteTrails")
    @ResponseBody
    public List<Trail> getFavoriteTrails(int userID) {
        return userService.fetchFavoriteTrails(userID);
    }

    /**
     * Add a favorite trail
     * @param trailID - trail
     * @param userID - user
     */
    @PostMapping("/addFavoriteTrail")
    @ResponseBody
    public Trail addFavoriteTrail(int trailID, int userID) {
        try {
            Trail foundTrail = trailService.findTrailByID(trailID);
            User foundUser = userService.findUserByID(userID);
            userService.addFavoriteTrail(foundUser, foundTrail);
            return foundTrail;
        } catch(Exception e) {
            logger.severe("Error adding favorite trail: " + e);
            return new Trail();
        }
    }

    /**
     * Delete a trail
     * @param trailID - trail
     */
    @PostMapping("/deleteFavoriteTrail")
    @ResponseBody
    public void deleteFavoriteTrail(int userID, int trailID) {
        try {
            userService.deleteFavoriteTrail(userID, trailID);
        } catch(Exception e) {
           logger.severe("Error deleting trail: " + e);
        }
    }

    /**
     * Autocomplete for only trail names. Used for favorites/alerts page.
     * @param term - search term
     * @return - list of trails
     */
    @GetMapping("/autocompleteTrailName")
    @ResponseBody
    public List<LabelValue> autocompleteTrailName(@RequestParam(value="term", required = false, defaultValue="") String term) {
        List<Trail> allTrails = trailService.fetchAllTrails();
        List<LabelValue> trailData = new ArrayList<>();
        for (Trail trail : allTrails) {
            LabelValue labelValue = new LabelValue();
            if (trail.getName().toLowerCase().contains(term.toLowerCase())) {
                labelValue.setLabel(trail.getName());
                labelValue.setValue(trail.getTrailID());
                trailData.add(labelValue);
            }
        }
        return trailData;
    }

    @GetMapping("/autocompleteTrailDifficulty")
    @ResponseBody
    public Set<LabelValue> autocompleteTrailDifficulty(@RequestParam(value="term", required = false, defaultValue="") String term) {
        List<Trail> allTrails = trailService.fetchAllTrails();
        Set<LabelValue> trailData = new HashSet<>();
        for (Trail trail : allTrails) {
            LabelValue labelValue = new LabelValue();
            if (trail.getDifficulty().toLowerCase().contains(term.toLowerCase())) {
                labelValue.setLabel(trail.getDifficulty());
                trailData.add(labelValue);
            }
        }
        return trailData;
    }

    @GetMapping("/autocompleteTrailType")
    @ResponseBody
    public Set<LabelValue> autocompleteTrailType(@RequestParam(value="term", required = false, defaultValue="") String term) {
        List<Trail> allTrails = trailService.fetchAllTrails();
        Set<LabelValue> trailData = new HashSet<>();
        for (Trail trail : allTrails) {
            LabelValue labelValue = new LabelValue();
            if (trail.getDifficulty().toLowerCase().contains(term.toLowerCase())) {
                labelValue.setLabel(trail.getTrailType());
                trailData.add(labelValue);
            }
        }
        return trailData;
    }

    @GetMapping("/autocompleteZipCode")
    @ResponseBody
    public Set<LabelValue> autocompleteZipCode(@RequestParam(value="term", required = false, defaultValue="") String term) {
        List<Trail> allTrails = trailService.fetchAllTrails();
        Set<LabelValue> trailData = new HashSet<>();
        for (Trail trail : allTrails) {
            LabelValue labelValue = new LabelValue();
            if (trail.getDifficulty().toLowerCase().contains(term.toLowerCase())) {
                labelValue.setLabel(trail.getZipCode());
                trailData.add(labelValue);
            }
        }
        return trailData;
    }

    /**
     * Autocomplete for favorite trails
     * @param userID - user ID
     * @param term - search term
     * @return - autocomplete
     */
    @PostMapping("/autocompleteFavoriteTrails")
    @ResponseBody
    public List<LabelValue> autocompleteFavoriteTrails(int userID, String term) {
        List<Trail> allTrails = userService.fetchFavoriteTrails(userID);
        List<LabelValue> trailData = new ArrayList<>();
        for (Trail trail : allTrails) {
            LabelValue labelValue = new LabelValue();
            if (trail.getName().toLowerCase().contains(term.toLowerCase())) {
                labelValue.setLabel(trail.getName());
                labelValue.setValue(trail.getTrailID());
                trailData.add(labelValue);
            }
        }
        return trailData;
    }

    /**
     * Handle the login endpoint and return a page.
     * @return login page
     */
    @RequestMapping("/Login")
    public String login(Model model) {
        User user = new User();
        model.addAttribute("User", user);
        return "Login";
    }

    /**
     * Check if a user exists and output result in JSON
     * @param email - user email
     * @param password - user password
     * @return - found user
     */
    @PostMapping("/loginUser")
    @ResponseBody
    public User loginUser(String email, String password) {
        return userService.findUser(email, password);
    }

    /**
     * Create a new user
     * @param user - user
     * @return - newly created user
     * @throws Exception - handle errors
     */
    @PostMapping("/createUser")
    @ResponseBody
    public User createUser(User user) throws Exception {
        User foundUser = userService.findExistingEmail(user.getEmail());
        if (foundUser.getUserID() == 0) {
            // Email doesn't exist, we can create this new user
            return userService.save(user);
        } else {
            // Email already exists, return blank user
            return new User();
        }
    }

    /**
     * Handle the create endpoint and return a page.
     * @return create page
     */
    @RequestMapping("/CreateTrail")
    public String create() {
        return "CreateTrail";
    }

    /**
     * Comprehensive autocomplete to search for trail name, type, difficulty, and zip code
     * @param term - search term
     * @return - list of trails
     */
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
            Alert newAlert = new Alert();
            newAlert.setAlertText(alertText);

            Trail trail = trailService.findTrailByID(trailID);
            newAlert.setTrail(trail);

            User user = userService.findUserByID(userID);
            newAlert.setUser(user);

            return alertService.save(newAlert);
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
