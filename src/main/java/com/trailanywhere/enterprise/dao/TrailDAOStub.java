package com.trailanywhere.enterprise.dao;

import com.trailanywhere.enterprise.dto.Trail;

import java.util.ArrayList;
import java.util.List;

public class TrailDAOStub implements ITrailDAO{

    List<Trail> allTrails = new ArrayList<Trail>();

    @Override
    public Trail save(Trail trail) throws Exception {
        return null;
    }

    @Override
    public void delete(Trail trail) throws Exception {

    }

    @Override
    public List<Trail> fetchAll() {
        return allTrails;
    }
}
