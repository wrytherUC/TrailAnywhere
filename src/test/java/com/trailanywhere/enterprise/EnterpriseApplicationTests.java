package com.trailanywhere.enterprise;

import com.fasterxml.jackson.databind.JsonNode;
import com.trailanywhere.enterprise.dao.ITrailDAO;
import com.trailanywhere.enterprise.dto.Trail;
import com.trailanywhere.enterprise.dto.User;
import com.trailanywhere.enterprise.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Handle all tests for TrailService.
 */
@SpringBootTest
class EnterpriseApplicationTests {

    @Autowired
    private ITrailService trailService;
    private Trail trail = new Trail();

    @MockBean
    private ITrailDAO trailDAO;

    private ArrayList<Trail> trailList = new ArrayList<>();

    @Autowired
    private IUserService userService;
    private User user = new User();

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
    void fetchTrailByName_returnTrailID1ForTrailForrestPark() throws Exception {
        givenTrailDataIsAvailable();
        whenSearchTrailWithNameForrestPark();
        thenReturnTrailID1TrailForForrestPark();
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
    void fetchTrailsByDifficulty() throws Exception {
        givenTrailDataIsAvailable();
        whenSearchTrailWithDifficultyHard();
        thenReturnTrailsWithHardDifficulty();
    }

    private void givenTrailDataIsAvailable() throws Exception {
        Mockito.when(trailDAO.save(trail)).thenReturn(trail);
        trailService = new TrailServiceStub(trailDAO);
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
    void fetchTrailsWithSameZipCode() throws Exception {
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
    void fetchTrailWeatherFromZipCode() throws Exception {
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
    void fetchTrailWeatherWithCoordinates() throws Exception {
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

    private void whenUserIsLoggedIn() {
        user.setName("Jacob");
        userService.addUser(user);
        assertTrue(userService.loginUser(user));
    }

    /**
     * Test for saving trails, use Mockito to mock trail DAO and verify it was used atleast once
     */
    @Test
    void createNewTrailAndValidateTheReturnDataFromTrailService() throws Exception {
        givenTrailDataIsAvailable();
        whenUserIsLoggedIn();
        whenUserEntersNewTrailData();
        thenCreateNewTrailAndReturnItFromService();
    }

    private void whenUserEntersNewTrailData() {
        trail.setName("HiddenTrail");
        trail.setZipCode("45201");
    }

    private void thenCreateNewTrailAndReturnItFromService() throws Exception {
        Trail createdTrail = trailService.save(trail);
        assertEquals(trail, createdTrail);
        verify(trailDAO, atLeastOnce()).save(trail);
    }

    /**
     * Test deleting a trail
     * @throws Exception - handle errors
     */
    @Test
    void deleteTrail() throws Exception {
        givenTrailDataIsAvailable();
        whenUserEntersNewTrailData();
        thenDeleteTrail();
    }

    private void thenDeleteTrail() throws Exception {
        trailDAO.delete(trail);
        verify(trailDAO, atLeastOnce()).delete(trail);
    }
}
