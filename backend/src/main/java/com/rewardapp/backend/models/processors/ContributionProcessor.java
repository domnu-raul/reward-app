package com.rewardapp.backend.models.processors;

import com.rewardapp.backend.controllers.ContributionController;
import com.rewardapp.backend.controllers.RecyclingCenterController;
import com.rewardapp.backend.models.Contribution;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ContributionProcessor implements RepresentationModelProcessor<Contribution> {

    @Override
    public Contribution process(Contribution model) {
        model.add(
                linkTo(methodOn(ContributionController.class).get(model.getId(), null)).withRel("details"),
                linkTo(methodOn(ContributionController.class).getAll(null, null)).withRel("all")
        );

        if (model.getRecyclingCenterId() != null) {
            model.add(
                    linkTo(methodOn(RecyclingCenterController.class).get(model.getRecyclingCenterId(), null)).withRel("recycling_center")
            );
        }
        return model;
    }
}
