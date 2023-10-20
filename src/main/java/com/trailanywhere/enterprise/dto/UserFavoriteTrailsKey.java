package com.trailanywhere.enterprise.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class UserFavoriteTrailsKey implements Serializable {
    @Column(name = "userID")
    int userID;

    @Column(name = "trailID")
    int trailID;
}
