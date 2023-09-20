package com.trailanywhere.enterprise.service;

import com.trailanywhere.enterprise.dto.Trail;
import org.springframework.stereotype.Component;

@Component
public class TrailServiceStub implements ITrailService {
    @Override
    public Trail fetchByTrailName(String trailName) {
        Trail trail = new Trail();
        trail.setTrailID(1);
        trail.setName("Forrest Park");
        return trail;
    }
}
