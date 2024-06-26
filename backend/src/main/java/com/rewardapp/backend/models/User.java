package com.rewardapp.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends RepresentationModel<User> {
    private Long id;
    private String username;
    private String email;
    private Boolean verified;
    private String registerDate;
    private UserType type;

    public enum UserType {
        ADMIN, USER
    }
}
