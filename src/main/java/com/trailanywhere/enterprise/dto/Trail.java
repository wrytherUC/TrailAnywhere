package com.trailanywhere.enterprise.dto;

import jakarta.persistence.*;
import lombok.Data;

@Entity
public @Data
class Trail {
    @Id
    @GeneratedValue
    private int trailID;
    private String name;
    private String difficulty;
    private String terrain;
    private String trailType;
    private String address;
    private String latitude;
    private String longitude;
    private String zipCode;
    @OneToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private User user;
    @OneToOne(mappedBy = "trail")
    private Alert alert;
}
