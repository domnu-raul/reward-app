package com.rewardapp.backend.models.processors;

import com.rewardapp.backend.controllers.ContributionController;
import com.rewardapp.backend.models.UserData;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserDataProcessor implements RepresentationModelProcessor<UserData> {
    @Override
    public UserData process(UserData model) {
        return model.add(
                linkTo(methodOn(ContributionController.class).getAll(null)).withRel("contributions")
        );
    }
}
