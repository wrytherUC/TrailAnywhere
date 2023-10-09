package com.trailanywhere.enterprise.dto;

import jakarta.persistence.*;
import lombok.Data;

@Entity
public @Data
class Alert {
    @Id
    @GeneratedValue
    private int alertID;
    @OneToOne
    @JoinColumn(name = "trailID", referencedColumnName = "trailID")
    private Trail trail;
    private String alertText;
    @OneToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private User user;
}
