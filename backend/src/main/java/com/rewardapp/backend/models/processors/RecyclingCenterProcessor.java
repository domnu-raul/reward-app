package com.rewardapp.backend.models.processors;

import com.rewardapp.backend.controllers.RecyclingCenterController;
import com.rewardapp.backend.models.RecyclingCenter;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class RecyclingCenterProcessor implements RepresentationModelProcessor<RecyclingCenter> {
    @Override
    public RecyclingCenter process(RecyclingCenter model) {
        return model.add(
                linkTo(methodOn(RecyclingCenterController.class).get(model.getId(), null))
                        .withSelfRel(),
                linkTo(methodOn(RecyclingCenterController.class).getAll(null))
                        .withRel("all")
        );
    }
}
