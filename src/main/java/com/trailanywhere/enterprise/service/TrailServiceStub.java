package com.trailanywhere.enterprise.service;

import com.trailanywhere.enterprise.dto.Trail;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Contains hardcoded data for unit testing.
 */
@Component
public class TrailServiceStub implements ITrailService {
    /**
     * Fetches a trail with a specified name.
     * @param trailName - Name of the trail being searched for
     * @return - trail
     */
    @Override
    public Trail fetchByTrailName(String trailName) {
        Trail trail = new Trail();
        trail.setTrailID(1);
        trail.setName("Forrest Park");
        return trail;
    }

    /**
     * Fetches all trails by specified difficulty
     * @param difficulty - difficulty selected by user
     * @return - trails
     */
    @Override
    public ArrayList<Trail> fetchByDifficulty(String difficulty) {
        ArrayList<Trail> list = new ArrayList<>();
        Trail trailOne = new Trail();
        Trail trailTwo = new Trail();
        trailOne.setDifficulty("Hard");
        trailTwo.setDifficulty("Hard");
        list.add(trailOne);
        list.add(trailTwo);
        return list;
    }
}
