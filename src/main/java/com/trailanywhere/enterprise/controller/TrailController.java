package com.trailanywhere.enterprise.controller;

import com.trailanywhere.enterprise.dto.Trail;
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
import java.util.logging.Logger;


/**
 * This controller will handle all Trail endpoints.
 */
@Controller
public class TrailController {

    @Autowired
    ITrailService trailService;

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
    @PostMapping(value="/trail", consumes="application/json", produces="application/json")
    @ResponseBody
    public Trail createTrail(@RequestBody Trail trail) {
        Trail newTrail = null;
        try {
            newTrail = trailService.save(trail);
        } catch (Exception e) {
            logger.severe("Error creating Trail: " + e.getMessage());
        }
        return newTrail;
    }

    @GetMapping("/trail")
    @ResponseBody
    public List<Trail> fetchAllSpecimens() {
        return trailService.fetchAllTrails();
    }


    @GetMapping("/trail/{name}/")
    public ResponseEntity fetchTrailByName (@PathVariable("name") String name) {
        Trail foundTrail = trailService.fetchByTrailName("name");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(foundTrail, headers, HttpStatus.OK);
    }

    @PostMapping(value="/trail", consumes="application/json", produces="application/json")
    public ResponseEntity createSpecimen(@RequestBody Trail trail) {
        Trail newTrail = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            newTrail = trailService.save(trail);
        } catch (Exception e) {

            return new ResponseEntity(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(newTrail, headers, HttpStatus.OK);
    }

    @DeleteMapping("/trail/{name}/")
    public ResponseEntity deleteTrail(@PathVariable("name") Trail name) {
        logger.log(Level.INFO,"Entering delete trail endpoint" );
        try {
            trailService.delete(name);
            logger.log(Level.INFO,"Specimen with name " + name + " was deleted." );
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Unable to delete specimen with name: " + name + ". Message: " + e.getMessage(), e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


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
}
