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

    @Autowired
    private ITrailDAO trailDAO;

    /**
     * Insert user test data
     */
    // @EventListener(ApplicationReadyEvent.class)
    public void addUserData() {
        User user = new User();
        user.setName("Ken");
        user.setEmail("test@gmail.com");
        user.setPassword("password");
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
     */
    // @EventListener(ApplicationReadyEvent.class)
    public void addAlertData() {
        Alert alert = new Alert();
        Trail trail = new Trail();
        User user = new User();
        alert.setTrail(trail);
        alert.setAlertText("Flooding");
        alert.setUser(user);
    }
}
