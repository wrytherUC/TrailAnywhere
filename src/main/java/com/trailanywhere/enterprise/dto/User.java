package com.trailanywhere.enterprise.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;

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
    private ArrayList<Trail> trails;
    @OneToMany(mappedBy = "user")
    private ArrayList<Alert> alerts;
}
