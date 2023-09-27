package com.trailanywhere.enterprise.dao;

import com.trailanywhere.enterprise.dto.User;

/**
 * Interface for CRUD operations related to users
 */
public interface IUserDAO {
    /**
     * Save a user
     * @param user - user object
     * @return - new user
     * @throws Exception - handle errors
     */
    User save(User user) throws Exception;
}
