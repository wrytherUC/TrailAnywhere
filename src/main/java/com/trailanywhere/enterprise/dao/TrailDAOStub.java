package com.trailanywhere.enterprise.dao;

import com.trailanywhere.enterprise.dto.Trail;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TrailDAOStub implements ITrailDAO{

    Map<Integer, Trail> allTrails = new HashMap<>();

    @Override
    public Trail save(Trail trail) throws Exception {
        Integer trailID = trail.getTrailID();
        allTrails.put(trailID, trail);
        return trail;
    }

    @Override
    public void delete(Trail trail) throws Exception {

    }

    @Override
    public List<Trail> fetchAll() {
        List<Trail> returnTrails = new ArrayList<>(allTrails.values());
        return returnTrails;
    }

    @Override
    public Trail fetchByTrail(String trailName) {
        return null;
    }
}
