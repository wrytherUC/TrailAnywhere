package com.trailanywhere.enterprise.dto;

import lombok.Data;

public @Data
class Alert {
    private int alertID;
    private Trail trail;
    private String alertText;
    private User user;
}
