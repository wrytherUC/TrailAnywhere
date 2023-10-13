package com.trailanywhere.enterprise.dto;

import jakarta.persistence.*;
import lombok.Data;

@Entity
public @Data
class Alert {
    @Id
    @GeneratedValue
    private int alertID;
    @ManyToOne
    @JoinColumn(name = "trailID", referencedColumnName = "trailID")
    private Trail trail;
    private String alertText;
    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private User user;
}
