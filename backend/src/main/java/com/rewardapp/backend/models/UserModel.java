package com.rewardapp.backend.models;

import com.rewardapp.backend.entities.User.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel extends RepresentationModel<UserModel> {
    private Long id;
    private String username;
    private String email;
    private Boolean verified;
    private String registerDate;
    private UserType type;
}
