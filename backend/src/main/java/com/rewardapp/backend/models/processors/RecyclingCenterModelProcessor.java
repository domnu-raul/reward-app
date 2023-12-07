package com.rewardapp.backend.models.processors;

import com.rewardapp.backend.controllers.RecyclingCenterController;
import com.rewardapp.backend.models.RecyclingCenterModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class RecyclingCenterModelProcessor implements RepresentationModelProcessor<RecyclingCenterModel> {
    @Override
    public RecyclingCenterModel process(RecyclingCenterModel model) {
        model.add(
                linkTo(methodOn(RecyclingCenterController.class).get(model.getId(), null))
                        .withSelfRel(),
                linkTo(methodOn(RecyclingCenterController.class).getAll(null))
                        .withRel("all")
        );
        return model;
    }
}
