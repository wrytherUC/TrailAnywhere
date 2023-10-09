package com.trailanywhere.enterprise.dto;

import jakarta.persistence.*;
import lombok.Data;

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
    @OneToOne(mappedBy = "user")
    private Trail trail;
    @OneToOne(mappedBy = "user")
    private Alert alert;
}
