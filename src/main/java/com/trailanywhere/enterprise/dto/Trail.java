package com.trailanywhere.enterprise.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Trail {
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
    @ToString.Exclude
    private List<UserFavoriteTrails> userFavoriteTrails;
    @OneToMany(mappedBy = "trail")
    @ToString.Exclude
    private List<Alert> alerts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Trail trail = (Trail) o;
        return trailID == trail.trailID;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
