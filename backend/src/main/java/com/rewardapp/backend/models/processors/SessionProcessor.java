package com.rewardapp.backend.models.processors;

import com.rewardapp.backend.controllers.AuthController;
import com.rewardapp.backend.models.Session;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SessionProcessor implements RepresentationModelProcessor<Session> {
    @Override
    public Session process(Session model) {
        return model.add(
                linkTo(methodOn(AuthController.class).logout(null)).withRel("logout")
                //linkTo(methodOn(UserController.class).getUserById(model.getUserId())).withRel("user")
        );
    }
}
