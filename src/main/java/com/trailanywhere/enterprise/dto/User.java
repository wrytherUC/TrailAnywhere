package com.trailanywhere.enterprise.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Users") // "user" is a reserved keyword in SQL
public @Data
class User {
    @Id
    @GeneratedValue
    private int userID;
    private String name;
    private String email;
    private String password;
    @OneToMany(mappedBy = "user")
    private List<UserFavoriteTrails> userFavoriteTrails;
    @OneToMany(mappedBy = "user")
    private List<Alert> alerts;
}
