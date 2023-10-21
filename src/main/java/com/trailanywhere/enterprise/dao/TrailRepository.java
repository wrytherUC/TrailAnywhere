package com.trailanywhere.enterprise.dao;

import com.trailanywhere.enterprise.dto.Trail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Handles CRUD operations for trails
 */
@Repository
public class TrailRepository implements ITrailDAO {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Default constructor
     */
    public TrailRepository() {

    }

    /**
     * Initialize EntityManager for CRUD operations
     * @param entityManager - EntityManager object
     */
    public TrailRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
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
        entityManager.persist(trail);
        entityManager.flush();
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
        Query query = entityManager.createQuery("DELETE FROM Trail t WHERE t.trailID = :TRAIL");
        query.setParameter("TRAIL", trail.getTrailID());
        query.executeUpdate();
    }

    /**
     * Get all trails
     * @return - list of trails
     */
    @Override
    public List<Trail> fetchAllTrails() {
        TypedQuery<Trail> query = entityManager.createQuery("SELECT t FROM Trail t", Trail.class);
        return query.getResultList();
    }

    /**
     * Get specific trail by name
     * @param trailName - name
     * @return - trail
     */
    @Override
    public Trail fetchByTrail(String trailName) {
        try {
            TypedQuery<Trail> query = entityManager.createQuery("SELECT t from Trail t WHERE t.name = :NAME", Trail.class);
            query.setParameter("NAME", trailName);
            return query.getSingleResult();
        } catch(Exception e) {
            // If no trail is found, return empty trail
            return new Trail();
        }

    }

    /**
     * Get trails by difficulty
     * @param difficulty - difficulty selected by user
     * @return - list of trails
     */
    @Override
    public List<Trail> fetchByDifficulty(String difficulty) {
        TypedQuery<Trail> query = entityManager.createQuery("SELECT t FROM Trail t WHERE t.difficulty = :DIFFICULTY", Trail.class);
        query.setParameter("DIFFICULTY", difficulty);
        return query.getResultList();
    }

    /**
     * Get trails by zip code
     * @param zipCode - zip code selected by user
     * @return - list of trails
     */
    @Override
    public List<Trail> fetchByZipCode(String zipCode) {
        TypedQuery<Trail> query = entityManager.createQuery("SELECT t FROM Trail t WHERE t.zipCode = :ZIP_CODE", Trail.class);
        query.setParameter("ZIP_CODE", zipCode);
        return query.getResultList();
    }

    /**
     * Get trails by coordinates
     * @param latitude - provided latitude coordinate
     * @param longitude - provided longitude coordinate
     * @return - list of trails
     */
    @Override
    public Trail fetchByCoordinates(String latitude, String longitude) {
        TypedQuery<Trail> query = entityManager.createQuery("SELECT t from Trail t WHERE t.latitude = :LATITUDE AND t.longitude = :LONGITUDE", Trail.class);
        query.setParameter("LATITUDE", latitude);
        query.setParameter("LONGITUDE", longitude);
        return query.getSingleResult();
    }
}
