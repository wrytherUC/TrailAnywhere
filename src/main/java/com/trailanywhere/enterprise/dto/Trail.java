package com.trailanywhere.enterprise.dto;

import lombok.Data;

public @Data
class Trail {
    private int trailID;
    private String name;
    private String difficulty;
    private String terrain;
    private String trailType;
    private String address;
    private String latitude;
    private String longitude;
    private String zipCode;
}
