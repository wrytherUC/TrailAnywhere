package com.trailanywhere.enterprise.dto;

import lombok.Data;

public @Data
class Alert {
    // Might need to change trail & user from strings to objects(?)
    private String trail;
    private String alertText;
    private String user;
}
