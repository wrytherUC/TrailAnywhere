package com.trailanywhere.enterprise.dto;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name="Users") // "user" is a reserved keyword in SQL
public @Getter
@Setter
@ToString
@RequiredArgsConstructor
class User {
    @Id
    @GeneratedValue
    private int userID;
    private String name;
    private String email;
    private String password;
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<UserFavoriteTrails> userFavoriteTrails;
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Alert> alerts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return userID == user.userID;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
