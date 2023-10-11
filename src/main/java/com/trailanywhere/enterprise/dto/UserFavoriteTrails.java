package com.trailanywhere.enterprise.dto;

import jakarta.persistence.*;
import lombok.Data;

@Entity
public @Data
class UserFavoriteTrails {
    @EmbeddedId
    UserFavoriteTrailsKey id;

    @ManyToOne
    @MapsId("userID")
    @JoinColumn(name = "userID")
    User user;

    @ManyToOne
    @MapsId("trailID")
    @JoinColumn(name = "trailID")
    Trail trail;
}
