package com.trailanywhere.enterprise.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;

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
    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private User user;
    @OneToMany(mappedBy = "trail")
    private ArrayList<Alert> alerts;
}
