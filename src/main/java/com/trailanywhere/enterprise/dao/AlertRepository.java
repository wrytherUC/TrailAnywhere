package com.trailanywhere.enterprise.dao;

import com.trailanywhere.enterprise.dto.Alert;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        entityManager.persist(alert.getUser());
        entityManager.persist(alert.getTrail());
        entityManager.persist(alert);
        entityManager.flush();
        return alert;
    }

    /**
     * Delete an alert
     * @param alert - alert to be deleted
     * @throws Exception - handle errors
     */
    @Override
    @Transactional
    public void delete(Alert alert) throws Exception {
        TypedQuery<Alert> query = entityManager.createQuery("DELETE FROM Alert a WHERE a.alertID = :ALERT", Alert.class);
        query.setParameter("ALERT", alert.getAlertID());
        query.executeUpdate();
    }

    /**
     * Get all alerts
     * @return - list of alerts
     */
    @Override
    public List<Alert> fetchAllAlerts() {
        TypedQuery<Alert> query = entityManager.createQuery("SELECT a FROM Alert a", Alert.class);
        return query.getResultList();
    }
}
