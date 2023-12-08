package com.rewardapp.backend.models.processors;

import com.rewardapp.backend.controllers.ContributionController;
import com.rewardapp.backend.controllers.RecyclingCenterController;
import com.rewardapp.backend.models.ContributionDetails;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ContributionDetailsProcessor implements RepresentationModelProcessor<ContributionDetails> {

    @Override
    public ContributionDetails process(ContributionDetails model) {
        return model.add(
                linkTo(methodOn(ContributionController.class).get(model.getId(), null)).withSelfRel(),
                linkTo(methodOn(RecyclingCenterController.class).get(model.getRecyclingCenter().getId(), null)).withRel("recycling_center"),
                linkTo(methodOn(ContributionController.class).getAll(null)).withRel("all")
        );
    }
}
