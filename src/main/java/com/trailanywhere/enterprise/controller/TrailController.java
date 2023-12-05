package com.trailanywhere.enterprise.controller;

import com.trailanywhere.enterprise.dto.LabelValue;
import com.trailanywhere.enterprise.dto.Trail;
import com.trailanywhere.enterprise.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Level;
import org.springframework.ui.Model;

import java.util.logging.Logger;

/**
 * This controller will handle all Trail endpoints.
 */
@Controller
public class TrailController {

    TrailService trailService;
    AlertService alertService;
    UserService userService;

    @Autowired
    public TrailController(TrailService trailService, AlertService alertService, UserService userService) {
        this.trailService = trailService;
        this.alertService = alertService;
        this.userService = userService;
    }

    private static final Logger logger = Logger.getLogger(TrailController.class.getName());

    /**
     * Handle the root endpoint and return the homepage.
     * @return homepage
     */
    @RequestMapping("/")
    public String index() {
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

    /**
     * Endpoint to show all trails
     * @return - JSON of all trails
     */
    @GetMapping("/trail")
    @ResponseBody
    public List<Trail> fetchAllTrails() {
        return trailService.fetchAllTrails();
    }


    /**
     * Endpoint to show a trail by its name
     * @param name - trail name
     * @return - trail JSON
     */
    @GetMapping("/trail/{name}/")
    public ResponseEntity<Trail> fetchTrailByName (@PathVariable("name") String name) {
        Trail foundTrail = trailService.fetchByTrailName(name);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (foundTrail != null) {
            return new ResponseEntity(foundTrail, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity("Trail not found", headers, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to delete a trail
     * @param trailID - ID of trail
     * @return - HTTP status
     */
    @DeleteMapping("/trail/{trailID}/")
    public ResponseEntity<Void> deleteTrail(@PathVariable("trailID") int trailID) {
        logger.log(Level.INFO,"Entering delete trail endpoint" );
        try {
            trailService.delete(trailID);
            logger.log(Level.INFO,"Trail with ID " + trailID + " was deleted." );
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Unable to delete trail with name: " + trailID + ". Message: " + e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Link users to corresponding search pages
     * @param searchType - search term
     * @param model - model
     * @return - view
     */
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

    /**
     * Show search results
     * @param searchTerm - search term
     * @param model - model
     * @return - view and trails
     */
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

    /**
     * Get all trails by their difficulty
     * @param searchTerm - search term
     * @param model - model
     * @return - view and trails
     */
    @GetMapping("/trailsByDifficulty")
    public String trailsByDifficulty(@RequestParam(value="searchTerm", required=false, defaultValue="None")  String searchTerm, Model model) {

        searchTerm = searchTerm.trim();
        List<Trail> trails;

        trails = trailService.fetchByDifficulty(searchTerm);

        model.addAttribute("trails", trails);

        return "trails";

    }

    /**
     * Get all trails by their name
     * @param searchTerm - search term
     * @param model - model
     * @return - view and trails
     */
    @GetMapping("/trailsByName")
    public String trailsByName(@RequestParam(value="searchTerm", required=false, defaultValue="None")  String searchTerm, Model model) {

        searchTerm = searchTerm.trim();
        Trail trail;

        trail = trailService.fetchByTrailName(searchTerm);

        model.addAttribute("trails", trail);
        return "trails";

    }

    /**
     * Get all trails by the same type
     * @param searchTerm - search term
     * @param model - model
     * @return - view and trails
     */
    @GetMapping("/trailsByType")
    public String trailsByType(@RequestParam(value="searchTerm", required=false, defaultValue="None")  String searchTerm, Model model) {

        searchTerm = searchTerm.trim();
        List<Trail> trails;

        trails = trailService.fetchByTrailType(searchTerm);

        model.addAttribute("trails", trails);
        return "trails";

    }

    /**
     * Get all trails with the same zip code
     * @param searchTerm - search term
     * @param model - model
     * @return - view and trails
     */
    @GetMapping("/trailsByZipCode")
    public String trailsByZipCode(@RequestParam(value="searchTerm", required=false, defaultValue="None")  String searchTerm, Model model) {

        searchTerm = searchTerm.trim();
        List<Trail> trails;

        trails = trailService.fetchByZipCode(searchTerm);

        model.addAttribute("trails", trails);
        return "trails";

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

    /**
     * Get all trail difficulties
     * @param term - search term
     * @return - all difficulties
     */
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

    /**
     * Get all trail types
     * @param term - search term
     * @return - all trail types
     */
    @GetMapping("/autocompleteTrailType")
    @ResponseBody
    public Set<LabelValue> autocompleteTrailType(@RequestParam(value="term", required = false, defaultValue="") String term) {
        List<Trail> allTrails = trailService.fetchAllTrails();
        Set<LabelValue> trailData = new HashSet<>();
        for (Trail trail : allTrails) {
            LabelValue labelValue = new LabelValue();
            if (trail.getTrailType().toLowerCase().contains(term.toLowerCase())) {
                labelValue.setLabel(trail.getTrailType());
                trailData.add(labelValue);
            }
        }
        return trailData;
    }

    /**
     * Get all zip codes from trails
     * @param term - search term
     * @return - Hashset containing all zip codes
     */
    @GetMapping("/autocompleteZipCode")
    @ResponseBody
    public Set<LabelValue> autocompleteZipCode(@RequestParam(value="term", required = false, defaultValue="") String term) {
        List<Trail> allTrails = trailService.fetchAllTrails();
        Set<LabelValue> trailData = new HashSet<>();
        for (Trail trail : allTrails) {
            LabelValue labelValue = new LabelValue();
            if (trail.getZipCode().toLowerCase().contains(term.toLowerCase())) {
                labelValue.setLabel(trail.getZipCode());
                trailData.add(labelValue);
            }
        }
        return trailData;
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
}