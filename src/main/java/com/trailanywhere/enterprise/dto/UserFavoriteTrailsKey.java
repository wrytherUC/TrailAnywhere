package com.trailanywhere.enterprise.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
public @Data
class UserFavoriteTrailsKey implements Serializable {
    @Column(name = "userID")
    int userID;

    @Column(name = "trailID")
    int trailID;
}
