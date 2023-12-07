package com.rewardapp.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    protected Long id;
    protected String username;
    protected String email;
    protected Boolean verified;
    protected Date register_date;
    protected UserType type;

    public enum UserType {
        ADMIN, USER;
    }
}
