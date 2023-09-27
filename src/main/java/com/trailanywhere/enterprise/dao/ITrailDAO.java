package com.trailanywhere.enterprise.dao;

import com.trailanywhere.enterprise.dto.Trail;

public interface ITrailDAO {
    Trail save(Trail trail) throws Exception;
}
