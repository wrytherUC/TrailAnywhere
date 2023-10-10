package com.trailanywhere.enterprise.dao;

import com.trailanywhere.enterprise.dto.Trail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Handles CRUD operations for trails
 */
@Repository
public class TrailRepository implements ITrailDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Default constructor
     */
    public TrailRepository() {

    }

    /**
     * Initialize EntityManager for CRUD operations
     * @param em - EntityManager object
     */
    public TrailRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * Insert a trail into the DB
     * @param trail - trail to be saved
     * @return - new trail
     * @throws Exception - error handling
     */
    @Override
    @Transactional
    public Trail save(Trail trail) throws Exception {
        em.persist(trail);
        em.flush();
        return trail;
    }

    /**
     * Delete a trail from the DB
     * @param trail - trail to be deleted
     * @throws Exception - handle error
     */
    @Override
    @Transactional
    public void delete(Trail trail) throws Exception {
        TypedQuery<Trail> query = em.createQuery("DELETE FROM Trail t WHERE t.trailID = :TRAIL", Trail.class);
        query.setParameter("TRAIL", trail.getTrailID());
        query.executeUpdate();
    }

    /**
     * Get all trails
     * @return - list of trails
     */
    @Override
    public ArrayList<Trail> fetchAllTrails() {
        Query query = em.createQuery("SELECT t FROM Trail t");
        return (ArrayList<Trail>) query.getResultList();
    }

    /**
     * Get specific trail by name
     * @param trailName - name
     * @return - trail
     */
    @Override
    public Trail fetchByTrail(String trailName) {
        TypedQuery<Trail> query = em.createQuery("SELECT t from Trail t WHERE t.name = :NAME", Trail.class);
        query.setParameter("NAME", trailName);
        return query.getSingleResult();
    }

    /**
     * Get trails by difficulty
     * @param difficulty - difficulty selected by user
     * @return - list of trails
     */
    @Override
    public ArrayList<Trail> fetchByDifficulty(String difficulty) {
        Query query = em.createQuery("SELECT t FROM Trail t WHERE t.difficulty = :DIFFICULTY");
        query.setParameter("DIFFICULTY", difficulty);
        return (ArrayList<Trail>) query.getResultList();
    }

    /**
     * Get trails by zip code
     * @param zipCode - zip code selected by user
     * @return - list of trails
     */
    @Override
    public ArrayList<Trail> fetchByZipCode(String zipCode) {
        Query query = em.createQuery("SELECT t FROM Trail t WHERE t.zipCode = :ZIP_CODE");
        query.setParameter("ZIP_CODE", zipCode);
        return (ArrayList<Trail>) query.getResultList();
    }

    /**
     * Get trails by coordinates
     * @param latitude - provided latitude coordinate
     * @param longitude - provided longitude coordinate
     * @return - list of trails
     */
    @Override
    public ArrayList<Trail> fetchByCoordinates(String latitude, String longitude) {
        Query query = em.createQuery("SELECT t FROM Trail t WHERE t.latitude = :LATITUDE AND t.longitude = :LONGITUDE");
        query.setParameter("LATITUDE", latitude);
        query.setParameter("LONGITUDE", longitude);
        return (ArrayList<Trail>) query.getResultList();
    }
}
