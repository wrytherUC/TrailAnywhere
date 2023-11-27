package com.trailanywhere.enterprise.dao;

import com.trailanywhere.enterprise.dto.Alert;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Handle alert CRUD operations
 */
@Repository
public class AlertRepository implements IAlertDAO {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Default constructor
     */
    public AlertRepository(){}

    /**
     * Initialize EntityManager for CRUD operations
     * @param entityManager - EntityManager object
     */
    public AlertRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Save an alert to the DB
     * @param alert - alert object
     * @return - alert
     * @throws Exception - handle errors
     */
    @Override
    @Transactional
    public Alert save(Alert alert) throws Exception {
        // Handle alert saves if a user or trail already exists, else insert a new record of both
        if (alert.getUser().getUserID() != 0 && alert.getTrail().getTrailID() != 0) {
            entityManager.persist(alert);
        } else if (alert.getUser().getUserID() != 0) {
            entityManager.persist(alert.getTrail());
            entityManager.persist(alert);
        } else if (alert.getTrail().getTrailID() != 0) {
            entityManager.persist(alert.getUser());
            entityManager.persist(alert);
        } else {
            entityManager.persist(alert.getUser());
            entityManager.persist(alert.getTrail());
            entityManager.persist(alert);
        }
        entityManager.flush();
        return alert;
    }

    /**
     * Delete an alert
     * @param alertID - alert to be deleted
     * @throws Exception - handle errors
     */
    @Override
    @Transactional
    public void delete(int alertID) throws Exception {
        Query query = entityManager.createQuery("DELETE FROM Alert alert WHERE alert.alertID = :ALERT");
        query.setParameter("ALERT", alertID);
        query.executeUpdate();
    }

    /**
     * Get all alerts
     * @return - list of alerts
     */
    @Override
    public List<Alert> fetchAllAlerts() {
        TypedQuery<Alert> query = entityManager.createQuery("SELECT alert FROM Alert alert", Alert.class);
        return query.getResultList();
    }

    /**
     * Find alert based on its ID
     * @param alertID - alert ID
     * @return - trail
     */
    @Override
    public Alert findAlertByID(int alertID) {
        try {
            TypedQuery<Alert> query = entityManager.createQuery("SELECT alert FROM Alert alert WHERE alert.alertID = :ALERT", Alert.class);
            query.setParameter("ALERT", alertID);
            return query.getSingleResult();
        } catch(Exception e) {
            return new Alert();
        }
    }

    /**
     * Find alerts for a trail
     * @param trailID - trail ID
     * @return - list of alerts
     */
    @Override
    public List<Alert> findAlertsForTrail(int trailID) {
        TypedQuery<Alert> query = entityManager.createQuery("SELECT alert FROM Alert alert WHERE alert.trail.trailID = :TRAIL", Alert.class);
        query.setParameter("TRAIL", trailID);
        return query.getResultList();
    }

    /**
     * Find all alerts made by a user
     * @param userID - user
     * @return - list of alerts
     */
    @Override
    public List<Alert> findAlertsForUser(int userID) {
        TypedQuery<Alert> query = entityManager.createQuery("SELECT alert FROM Alert alert WHERE alert.user.userID = :USER", Alert.class);
        query.setParameter("USER", userID);
        return query.getResultList();
    }
}
