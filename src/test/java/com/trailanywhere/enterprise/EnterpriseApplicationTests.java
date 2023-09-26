package com.trailanywhere.enterprise;

import com.trailanywhere.enterprise.dto.Trail;
import com.trailanywhere.enterprise.service.ITrailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EnterpriseApplicationTests {

    @Autowired
    private ITrailService trailService;
    private Trail trail;
    private ArrayList<Trail> trailList = new ArrayList<>();

    @Test
    void contextLoads() {
    }

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

}
