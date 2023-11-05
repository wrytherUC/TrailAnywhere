package com.trailanywhere.enterprise.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
    @Column(unique = true)
    private String name;
    private String difficulty;
    private String terrain;
    private String trailType;
    private String address;
    private String latitude;
    private String longitude;
    private String zipCode;
    @JsonIgnore
    @OneToMany(mappedBy = "trail")
    @ToString.Exclude
    private List<UserFavoriteTrails> userFavoriteTrails;
    @JsonIgnore
    @OneToMany(mappedBy = "trail")
    @ToString.Exclude
    private List<Alert> alerts;

    public String toString() {
        return trailID + " " + name + " " + difficulty + " " + zipCode;
    }

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
