package com.trailanywhere.enterprise.dao;

import com.trailanywhere.enterprise.dto.Alert;
import com.trailanywhere.enterprise.dto.Trail;
import com.trailanywhere.enterprise.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Insert test data into the DB when app runs
 */
@Component
public class TestData {

    private ITrailDAO trailDAO;
    private IUserDAO userDAO;
    private IAlertDAO alertDAO;

    @Autowired
    public TestData(ITrailDAO trailDAO, IUserDAO userDAO, IAlertDAO alertDAO) {
        this.trailDAO = trailDAO;
        this.userDAO = userDAO;
        this.alertDAO = alertDAO;
    }

    /**
     * Insert user test data
     * @throws Exception - handle errors
     */
    @EventListener(ApplicationReadyEvent.class)
    public void addUserData() throws Exception {
        User user = new User();
        user.setName("Ken");
        user.setEmail("test@gmail.com");
        user.setPassword("password");
        userDAO.save(user);
    }

    /**
     * Insert trail test data
     * @throws Exception - handle errors
     */
    @EventListener(ApplicationReadyEvent.class)
    public void addTrailData() throws Exception {
        Trail trailOne = new Trail();
        trailOne.setName("Bender Mountain Loop Trail");
        trailOne.setDifficulty("Moderate");
        trailOne.setTerrain("Hill");
        trailOne.setTrailType("Hiking");
        trailOne.setAddress("123 Test St");
        trailOne.setLatitude("39.15838");
        trailOne.setLongitude("-84.59775");
        trailOne.setZipCode("45211");
        trailDAO.save(trailOne);

        Trail trailTwo = new Trail();
        trailTwo.setName("Beechwood, Furnas, Ponderosa, and Red Oak Trail");
        trailTwo.setDifficulty("Moderate");
        trailTwo.setTerrain("Hill");
        trailTwo.setTrailType("Hiking");
        trailTwo.setAddress("124 Test St");
        trailTwo.setLatitude("39.16372");
        trailTwo.setLongitude("-84.45241");
        trailTwo.setZipCode("45212");
        trailDAO.save(trailTwo);

        Trail trailThree = new Trail();
        trailThree.setName("French Park");
        trailThree.setDifficulty("Moderate");
        trailThree.setTerrain("Hill");
        trailThree.setTrailType("Hiking");
        trailThree.setAddress("125 Test St");
        trailThree.setLatitude("39.18001");
        trailThree.setLongitude("-84.42146");
        trailThree.setZipCode("45213");
        trailDAO.save(trailThree);

        Trail trailFour = new Trail();
        trailFour.setName("Ault Forest Loop Trail");
        trailFour.setDifficulty("Easy");
        trailFour.setTerrain("Hill");
        trailFour.setTrailType("Hiking");
        trailFour.setAddress("126 Test St");
        trailFour.setLatitude("39.12040");
        trailFour.setLongitude("-84.54868");
        trailFour.setZipCode("45214");
        trailDAO.save(trailFour);

        Trail trailFive = new Trail();
        trailFive.setName("Stone Steps Loop");
        trailFive.setDifficulty("Hard");
        trailFive.setTerrain("Hill");
        trailFive.setTrailType("Hiking");
        trailFive.setAddress("127 Test St");
        trailFive.setLatitude("39.23810");
        trailFive.setLongitude("-84.45970");
        trailFive.setZipCode("45215");
        trailDAO.save(trailFive);
    }

    /**
     * Insert alert test data
     * @throws Exception - handle errors
     */
    @EventListener(ApplicationReadyEvent.class)
    public void addAlertData() throws Exception {
        Alert alert = new Alert();
        Trail trail = new Trail();
        User user = new User();

        user.setName("Jacob");
        user.setEmail("jacob@gmail.com");
        user.setPassword("password");
        trail.setName("Forrest Park");
        trail.setDifficulty("Easy");
        trail.setTerrain("Hill");
        trail.setTrailType("Hiking");
        trail.setAddress("123 Test St");
        trail.setLatitude("39.15838");
        trail.setLongitude("-84.59775");
        trail.setZipCode("45216");
        alert.setUser(user);
        alert.setTrail(trail);
        alert.setAlertText("Flooding");
        alertDAO.save(alert);
    }

    /**
     * Create a favorite trail
     * @throws Exception - handle errors
     */
    @EventListener(ApplicationReadyEvent.class)
    public void addFavoriteTrail() throws Exception {
        Trail trail = new Trail();
        trail.setName("Favorite Trail");
        trail.setDifficulty("Hard");
        trail.setTerrain("Hill");
        trail.setTrailType("Hiking");
        trail.setAddress("123 Test St");
        trail.setLatitude("39.25838");
        trail.setLongitude("-84.59777");
        trail.setZipCode("45217");
        User user = new User();
        user.setName("Favorite");
        user.setEmail("favorite@gmail.com");
        user.setPassword("password");
        trailDAO.save(trail);
        userDAO.save(user);
        userDAO.addFavoriteTrail(user, trail);
    }
}
