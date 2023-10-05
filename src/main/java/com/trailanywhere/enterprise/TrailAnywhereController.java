package com.classfollow.enterprise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;

@Controller
public class TrailAnywhereController {

    @Autowired
    IAlertService alertService;


    /**
     * Handle root endpoint and return to homepage
     *
     * @return homepage
     */
    @RequestMapping("/")
    public String index() {
        return "TrailFinder";
    }



    @GetMapping("/Trail")
    public ResponseEntity fetchAllTrails(){
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/Trail", consumes = "application/json", produces = "application/json")
    public Trail createTrail(@RequestBody Trail trail){

        return trail;
    }



    @GetMapping("/Alert")
    public ResponseEntity fetchAllAlerts() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/Alert", consumes = "application/json", produces = "application/json")
    public ResponseEntity createTrail(@RequestBody Alert alert) {
        Alert newAlert = null;
        try{
            newAlert = alertService.save(alert);
        } catch (Exception e){
            //logging
        }
        return Alert;
    }



    @GetMapping("/Trail")
    @ResponseBody
    public List<Trail> fetchAllTrails() {
        return TrailService.fetchAll();
    }


    @RequestMapping("/Alerts")
    public String alerts() {
        return "Alerts";
    }



    @GetMapping("/User")
    public ResponseEntity fetchAllUsers () {
        return new ResponseEntity(HttpStatus.OK);
}

    @PostMapping(value = "/User", consumes = "application/json", produces = "application/json")
    public ResponseEntity createUser (@RequestBody User user){

        return User;
    }




    /**
     * Handle login endpoint and return login page
     *
     * @return Login
     **/
    @RequestMapping("/Login")
    public String login() {
        return "Login";
    }


    /**
     * Handle favorites and return to favorites page
     *
     * @return Favorites
     */

    @RequestMapping("/Favorites")
    public String favorites() {
        return "Favorites";
    }


    }




