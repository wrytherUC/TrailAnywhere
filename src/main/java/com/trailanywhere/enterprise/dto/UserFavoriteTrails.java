package com.trailanywhere.enterprise.dto;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
public @Getter
@Setter
@ToString
@RequiredArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserFavoriteTrails that = (UserFavoriteTrails) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
