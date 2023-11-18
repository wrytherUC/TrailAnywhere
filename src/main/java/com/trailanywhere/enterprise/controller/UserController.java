package com.trailanywhere.enterprise.controller;

import com.trailanywhere.enterprise.dto.*;
import com.trailanywhere.enterprise.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This controller will handle all endpoints related to users.
 */
@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    TrailService trailService;
    private static final Logger logger = Logger.getLogger(TrailController.class.getName());

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
     * Delete a favorite trail
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
}
