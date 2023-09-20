package com.trailanywhere.enterprise;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EnterpriseApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void fetchTrailByName_returnTrailID1ForTrailForrestPark() {
        givenTrailDataIsAvailableWithTrailForrestPark();
        whenSearchTrailWithNameForrestPark();
        thenReturnTrailID1TrailForForrestPark();
    }

    private void givenTrailDataIsAvailableWithTrailForrestPark() {
    }

    private void whenSearchTrailWithNameForrestPark() {
    }

    private void thenReturnTrailID1TrailForForrestPark() {
        
    }

}
