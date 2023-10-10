package com.trailanywhere.enterprise.dao;

import com.trailanywhere.enterprise.dto.Trail;
import com.trailanywhere.enterprise.dto.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Handle CRUD operations for users
 */
@Repository
public class UserRepository implements IUserDAO {
    @PersistenceContext
    private EntityManager em;

    /**
     * Default constructor
     */
    public UserRepository(){}

    /**
     * Initialize EntityManager for CRUD operations
     * @param em - EntityManager object
     */
    public UserRepository(EntityManager em) {
        this.em = em;
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
        em.persist(user);
        em.flush();
        return user;
    }

    /**
     * Delete a user from the DB
     * @param user - user to be deleted
     * @throws Exception - handle errors
     */
    @Override
    @Transactional
    public void delete(User user) throws Exception {
        TypedQuery<User> query = em.createQuery("DELETE FROM User u WHERE u.userID = :USER", User.class);
        query.setParameter("USER", user.getUserID());
        query.executeUpdate();
    }

    /**
     * Fetch a user's favorite trails
     * @param user - user
     * @return - list of trails
     */
    @Override
    public <T> ArrayList<T> fetchFavoriteTrails(User user) {
        Query query = em.createNativeQuery("SELECT * FROM USERFAVORITETRAILS t WHERE t.USERID = ?1");
        query.setParameter(1, user.getUserID());
        return (ArrayList<T>) query.getResultList();
    }

    /**
     * Add a trail to a user's favorites list
     * @param user - user who's adding the trail
     * @param trail - trail to be added
     */
    @Override
    @Transactional
    public void addFavoriteTrail(User user, Trail trail) {
        Query query = em.createNativeQuery("INSERT INTO USERFAVORITETRAILS (TRAILID, USERID) VALUES (?, ?)");
        query.setParameter(1, trail.getTrailID());
        query.setParameter(2, user.getUserID());
        query.executeUpdate();
    }
}
