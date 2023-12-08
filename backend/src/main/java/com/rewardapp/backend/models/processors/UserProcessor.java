package com.rewardapp.backend.models.processors;

import com.rewardapp.backend.controllers.AuthController;
import com.rewardapp.backend.models.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserProcessor implements RepresentationModelProcessor<User> {
    @Override
    public @NotNull User process(User model) {
        return model.add(
                linkTo(methodOn(AuthController.class).logout(null))
                        .withRel("logout"),
                linkTo(methodOn(AuthController.class).login(null, null))
                        .withRel("login")
        );
    }
}
