package com.rewardapp.backend.models.processors;

import com.rewardapp.backend.controllers.AuthController;
import com.rewardapp.backend.models.UserModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelProcessor implements RepresentationModelProcessor<UserModel> {
    @Override
    public @NotNull UserModel process(UserModel model) {
        return model.add(
                linkTo(methodOn(AuthController.class).logout(null))
                        .withRel("logout"),
                linkTo(methodOn(AuthController.class).login(null, null))
                        .withRel("login")
        );
    }
}
