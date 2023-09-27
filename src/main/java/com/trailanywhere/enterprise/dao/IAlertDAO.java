package com.trailanywhere.enterprise.dao;

import com.trailanywhere.enterprise.dto.Alert;

public interface IAlertDAO {
    /**
     * Create an alert.
     * @param alert - alert object
     * @return - created alert
     */
    Alert save(Alert alert) throws Exception;
}
