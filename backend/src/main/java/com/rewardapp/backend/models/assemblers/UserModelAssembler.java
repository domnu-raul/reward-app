package com.rewardapp.backend.models.assemblers;

import com.rewardapp.backend.entities.User;
import com.rewardapp.backend.models.UserModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, UserModel> {
    @Override
    public UserModel toModel(User entity) {
        return new UserModel(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getVerified(),
                entity.getRegisterDate().toString(),
                entity.getType()
        );
    }
}
