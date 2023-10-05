package com.trailanywhere.enterprise.dto;

import lombok.Data;

public @Data
class User {
    private int userID;
    private String name;
    private String email;
    private String password;
}
