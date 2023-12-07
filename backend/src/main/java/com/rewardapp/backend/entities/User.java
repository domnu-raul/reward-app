package com.rewardapp.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    protected Long id;
    protected String username;
    protected String email;
    protected Boolean verified;
    protected Date registerDate;
    protected UserType type;

    public enum UserType {
        ADMIN, USER;

    }
}
