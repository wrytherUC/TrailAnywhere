package com.trailanywhere.enterprise.dao;

import com.trailanywhere.enterprise.dto.Trail;
import com.trailanywhere.enterprise.dto.User;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Handle CRUD operations for users
 */
@Repository
public class UserRepository implements IUserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Default constructor
     */
    public UserRepository(){}

    /**
     * Initialize EntityManager for CRUD operations
     * @param entityManager - EntityManager object
     */
    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Save a user to the DB
     * @param user - user object
     * @return - user
     * @throws Exception - handle errors
     */
    @Override
    @Transactional
    public User save(User user) throws Exception {
        entityManager.persist(user);
        entityManager.flush();
        return user;
    }

    /**
     * Delete a user from the DB
     * @param userID - user to be deleted
     * @throws Exception - handle errors
     */
    @Override
    @Transactional
    public void delete(int userID) throws Exception {
        Query query = entityManager.createQuery("DELETE FROM User u WHERE u.userID = :USER");
        query.setParameter("USER", userID);
        query.executeUpdate();
    }

    /**
     * Fetch a user's favorite trails
     *
     * @param userID - user
     * @return - list of trails
     */
    @Override
    public List<Trail> fetchFavoriteTrails(int userID) {
        TypedQuery<Trail> query = entityManager.createQuery("SELECT t FROM UserFavoriteTrails ut JOIN Trail t ON ut.trail.trailID = t.trailID WHERE ut.user.userID = :USER", Trail.class);
        query.setParameter("USER", userID);
        return query.getResultList();
    }

    /**
     * Add a trail to a user's favorites list
     * @param user - user who's adding the trail
     * @param trail - trail to be added
     */
    @Override
    @Transactional
    public void addFavoriteTrail(User user, Trail trail) {
        // If user/trail does not exist, insert them into the DB first
        if (user.getUserID() == 0 && trail.getTrailID() == 0) {
            entityManager.persist(user);
            entityManager.persist(trail);
            entityManager.flush();
        } else if (user.getUserID() == 0) {
            entityManager.persist(user);
            entityManager.flush();
        } else if (trail.getTrailID() == 0) {
            entityManager.persist(trail);
            entityManager.flush();
        }

        // Add a favorite trail
        Query query = entityManager.createNativeQuery("INSERT INTO USER_FAVORITE_TRAILS (TRAILID, USERID) SELECT t.TRAILID, u.USERID FROM TRAIL t, USERS u WHERE t.TRAILID = ?1 AND u.USERID = ?2");// "
        query.setParameter(1, trail.getTrailID());
        query.setParameter(2, user.getUserID());
        query.executeUpdate();
    }

    /**
     * Delete a favorite trail
     * @param userID - User
     * @param trailID - Trail to be unfavorited
     */
    @Override
    @Transactional
    public void deleteFavoriteTrail(int userID, int trailID) {
        Query query = entityManager.createNativeQuery("DELETE FROM USER_FAVORITE_TRAILS ut WHERE ut.USERID = ?1 AND ut.TRAILID = ?2");
        query.setParameter(1, userID);
        query.setParameter(2, trailID);
        query.executeUpdate();
    }

    /**
     * Find a user based on their credentials
     * @param email - email address
     * @param password - user password
     * @return - User
     */
    @Override
    public User findUser(String email, String password) {
        try {
            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :EMAIL AND u.password = :PASSWORD", User.class);
            query.setParameter("EMAIL", email);
            query.setParameter("PASSWORD", password);
            return query.getSingleResult();
        } catch (Exception e) {
            // If query fails to validate user, return an empty object
            return new User();
        }

    }

    /**
     * Find a user based on their ID
     * @param userID - user ID
     * @return - user
     */
    @Override
    public User findUserByID(int userID) {
        try {
            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.userID = :USER", User.class);
            query.setParameter("USER", userID);
            return query.getSingleResult();
        } catch (Exception e) {
            // If query fails to validate user, return an empty object
            return new User();
        }
    }

    /**
     * Check if an email already exists
     * @param email - email
     * @return - user
     */
    @Override
    public User findExistingEmail(String email) {
         try {
             TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :USER", User.class);
             query.setParameter("USER", email);
             return query.getSingleResult();
         } catch(Exception e) {
             return new User();
         }
    }
}
