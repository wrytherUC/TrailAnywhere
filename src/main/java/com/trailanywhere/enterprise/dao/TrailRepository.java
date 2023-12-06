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
     * @param trailID - trail to be deleted
     * @throws Exception - handle error
     */
    @Override
    @Transactional
    public void delete(int trailID) throws Exception {
        Query query = entityManager.createQuery("DELETE FROM Trail trail WHERE trail.trailID = :TRAIL");
        query.setParameter("TRAIL", trailID);
        query.executeUpdate();
    }

    /**
     * Get all trails
     * @return - list of trails
     */
    @Override
    public List<Trail> fetchAllTrails() {
        TypedQuery<Trail> query = entityManager.createQuery("SELECT trail FROM Trail trail", Trail.class);
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
            TypedQuery<Trail> query = entityManager.createQuery("SELECT trail from Trail trail WHERE trail.name = :NAME", Trail.class);
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
        TypedQuery<Trail> query = entityManager.createQuery("SELECT trail FROM Trail trail WHERE trail.difficulty = :DIFFICULTY", Trail.class);
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
        TypedQuery<Trail> query = entityManager.createQuery("SELECT trail FROM Trail trail WHERE trail.zipCode = :ZIP_CODE", Trail.class);
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
        try {
            TypedQuery<Trail> query = entityManager.createQuery("SELECT trail from Trail trail WHERE trail.latitude = :LATITUDE AND trail.longitude = :LONGITUDE", Trail.class);
            query.setParameter("LATITUDE", latitude);
            query.setParameter("LONGITUDE", longitude);
            return query.getSingleResult();
        } catch(Exception e) {
            return new Trail();
        }
    }

    /**
     * Find a trail based on its ID
     * @param trailID - trail ID
     * @return - trail
     */
    @Override
    public Trail findTrailByID(int trailID) {
        try {
            TypedQuery<Trail> query = entityManager.createQuery("SELECT trail from Trail trail WHERE trail.trailID = :TRAIL", Trail.class);
            query.setParameter("TRAIL", trailID);
            return query.getSingleResult();
        } catch(Exception e) {
            return new Trail();
        }
    }

    /**
     * Find a trail based on the provided trail type
     * @param trailType provided string, base search of off trailType
     * @return list of trails that match the trailType search
     */
    @Override
    public List<Trail> fetchByTrailType(String trailType) {
        TypedQuery<Trail> query = entityManager.createQuery("SELECT trail FROM Trail trail WHERE trail.trailType = :TRAIL_TYPE", Trail.class);
        query.setParameter("TRAIL_TYPE", trailType);
        return query.getResultList();
    }

    /**
     * Provide all trail difficulty types found in database
     * @return list of trail difficulty types from all trails
     */
    @Override
    public List<String> fetchAllDifficultyTypes() {
        TypedQuery<String> query = entityManager.createQuery("SELECT trail.difficulty FROM Trail trail", String.class);
        return query.getResultList();
    }

    /**
     * Provide all trail types found in database
     * @return list of trail types from all trails
     */
    public List<String> fetchAllTrailTypes() {
        TypedQuery<String> query = entityManager.createQuery("SELECT trail.trailType FROM Trail trail", String.class);
        return query.getResultList();
    }
}
