package com.trailanywhere.enterprise.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "alertID")
public class Alert {
    @Id
    @GeneratedValue
    private int alertID;
    @ManyToOne
    @JoinColumn(name = "trailID", referencedColumnName = "trailID")
    private Trail trail;
    private String alertText;
    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private User user;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || Hibernate.getClass(this) != Hibernate.getClass(object)) return false;
        Alert alert = (Alert) object;
        return alertID == alert.alertID;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
