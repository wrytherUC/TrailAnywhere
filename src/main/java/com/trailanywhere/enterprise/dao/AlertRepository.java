package com.trailanywhere.enterprise.dao;

import com.trailanywhere.enterprise.dto.Alert;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Handle alert CRUD operations
 */
@Repository
public class AlertRepository implements IAlertDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Default constructor
     */
    public AlertRepository(){}

    /**
     * Initialize EntityManager for CRUD operations
     * @param em - EntityManager object
     */
    public AlertRepository(EntityManager em) {
        this.em = em;
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
        em.persist(alert.getUser());
        em.persist(alert.getTrail());
        em.persist(alert);
        em.flush();
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
        TypedQuery<Alert> query = em.createQuery("DELETE FROM Alert a WHERE a.alertID = :ALERT", Alert.class);
        query.setParameter("ALERT", alert.getAlertID());
        query.executeUpdate();
    }

    /**
     * Get all alerts
     * @return - list of alerts
     */
    @Override
    public ArrayList<Alert> fetchAllAlerts() {
        Query query = em.createQuery("SELECT a FROM Alert a");
        return (ArrayList<Alert>) query.getResultList();
    }
}
