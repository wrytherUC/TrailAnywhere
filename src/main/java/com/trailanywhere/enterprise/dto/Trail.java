package com.trailanywhere.enterprise.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(mappedBy = "trail")
    private List<UserFavoriteTrails> userFavoriteTrails;
    @OneToMany(mappedBy = "trail")
    private List<Alert> alerts;
}
