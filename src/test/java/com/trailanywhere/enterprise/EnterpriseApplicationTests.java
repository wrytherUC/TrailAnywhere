package com.trailanywhere.enterprise;

import com.trailanywhere.enterprise.dto.Trail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EnterpriseApplicationTests {

    @Autowired
    private ITrailService trailService;
    private Trail trail;

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

}
