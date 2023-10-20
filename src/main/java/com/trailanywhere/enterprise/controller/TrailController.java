package com.trailanywhere.enterprise.controller;

import com.trailanywhere.enterprise.dto.Trail;
import com.trailanywhere.enterprise.service.ITrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
