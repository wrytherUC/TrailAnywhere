package com.trailanywhere.enterprise;

import com.fasterxml.jackson.databind.JsonNode;
import com.trailanywhere.enterprise.dto.Alert;
import com.trailanywhere.enterprise.dto.Trail;
import com.trailanywhere.enterprise.dto.User;
import com.trailanywhere.enterprise.service.IAlertService;
import com.trailanywhere.enterprise.service.ITrailService;
import com.trailanywhere.enterprise.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Handle all tests for TrailService.
 */
@SpringBootTest
class EnterpriseApplicationTests {

    @Autowired
    private ITrailService trailService;
    private Trail trail;
    private ArrayList<Trail> trailList = new ArrayList<>();

    @Autowired
    private IUserService userService;
    User user = new User();
    private ArrayList<User> userList = new ArrayList<>();

    @Autowired
    private IAlertService alertService;

    /**
     * Test if project compiles.
     */
    @Test
    void contextLoads() {
    }

    /**
     * Test fetching trail with a specific name.
     */
    @Test
    void fetchTrailByName_returnTrailID1ForTrailForrestPark() {
        givenTrailDataIsAvailableWithTrailNameForrestPark();
        whenSearchTrailWithNameForrestPark();
        thenReturnTrailID1TrailForForrestPark();
    }

    private void givenTrailDataIsAvailableWithTrailNameForrestPark() {
    }

    private void whenSearchTrailWithNameForrestPark() {
        trail = trailService.fetchByTrailName("Forrest Park");
    }

    private void thenReturnTrailID1TrailForForrestPark() {
        Integer trailID = trail.getTrailID();
        assertEquals(1, trailID);
    }

    /**
     * Test fetching trails with the same difficulty.
     */
    @Test
    void fetchTrailsByDifficulty() {
        givenTrailDataIsAvailable();
        whenSearchTrailWithDifficultyHard();
        thenReturnTrailsWithHardDifficulty();
    }

    private void givenTrailDataIsAvailable() {

    }

    private void whenSearchTrailWithDifficultyHard() {
        trailList = trailService.fetchByDifficulty("Hard");
    }

    private void thenReturnTrailsWithHardDifficulty() {
        boolean matchedList = true;
        String difficulty = "Hard";
        for (Trail trail : trailList) {
            if (!trail.getDifficulty().equals(difficulty)) {
                matchedList = false;
                break;
            }
        }
        assertTrue(matchedList);
    }

    /**
     * Test for returning trails with the same zip code.
     */
    @Test
    void fetchTrailsWithSameZipCode() {
        givenTrailDataIsAvailable();
        whenSearchTrailWithSameZipCode();
        thenReturnTrailsWithSameZipCode();
    }

    private void whenSearchTrailWithSameZipCode() {
        trailList = trailService.fetchByZipCode("45211");
    }

    private void thenReturnTrailsWithSameZipCode() {
        boolean matchedList = true;
        String zipCode = "45211";
        for (Trail trail : trailList) {
            if (!trail.getZipCode().equals(zipCode)) {
                matchedList = false;
                break;
            }
        }
        assertTrue(matchedList);
    }

    /**
     * Test for checking weather JSON data with provided zip code (convert zip code to coordinates for API)
     */
    @Test
    void fetchTrailWeatherFromZipCode() {
        givenTrailDataIsAvailable();
        whenSearchTrailWithSameZipCode();
        thenReturnTrailWeatherWithSameZipCode();
    }

    private void thenReturnTrailWeatherWithSameZipCode() {
        JsonNode node = trailService.getCurrentWeatherByZipCode("45211");

        // If coordinates are the same then it fetched the weather correctly
        assertEquals("39.13797", node.get("latitude").asText());
        assertEquals("-84.52533", node.get("longitude").asText());
    }

    /**
     * Test for finding trails with provided coordinates and return weather data
     */
    @Test
    void fetchTrailWeatherWithCoordinates() {
        givenTrailDataIsAvailable();
        whenSearchTrailWithCoordinates();
        thenReturnTrailWeatherWithCoordinates();
    }

    private void whenSearchTrailWithCoordinates() {
        trailList = trailService.fetchByCoordinates("39.13797", "-84.52533");
    }

    private void thenReturnTrailWeatherWithCoordinates() {
        JsonNode node = trailService.getCurrentWeather(trailList.get(0).getLatitude(), trailList.get(0).getLongitude());

        // If coordinates are the same then it fetched the weather correctly
        assertEquals("39.13797", node.get("latitude").asText());
        assertEquals("-84.52533", node.get("longitude").asText());
    }

    /**
     * Test for creating an alert with a provided trail name (user must be logged in).
     */
    @Test
    void createNewAlert() {
        givenTrailDataIsAvailable();
        whenUserIsLoggedIn();
        thenCreateAlertForTrail();
    }

    private void whenUserIsLoggedIn() {
        user.setName("Jacob");
        userService.addUser(user);
        userList = userService.fetchLoggedInUsers();
        assertTrue(userService.loginUser(user));
    }

    private void thenCreateAlertForTrail() {
        // Set test trail data
        Trail testTrail = new Trail();
        testTrail.setName("Forrest Park");
        trailService.addTrail(testTrail);
        trailList = trailService.fetchAlltrails();

        // Set test alert data
        Alert alert = new Alert();
        alert.setUser(user);
        alert.setTrail(testTrail);
        alertService.addAlert(alert);
        ArrayList<Alert> alertList = alertService.fetchAllAlerts();

        // Test conditions
        assertEquals(userList.get(0).getName(), alertList.get(0).getUser().getName());
        assertEquals(trailList.get(0).getName(), alertList.get(0).getTrail().getName());
    }

}
