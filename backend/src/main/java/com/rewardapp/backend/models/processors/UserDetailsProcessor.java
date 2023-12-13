package com.rewardapp.backend.models.processors;

import com.rewardapp.backend.controllers.ContributionController;
import com.rewardapp.backend.controllers.PurchaseController;
import com.rewardapp.backend.models.UserDetails;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UseerDetailsProcessor implements RepresentationModelProcessor<UserDetails> {
    @Override
    public UserDetails process(UserDetails model) {
        return model.add(
                linkTo(methodOn(ContributionController.class).getAll(null, null)).withRel("contributions"),
                linkTo(methodOn(PurchaseController.class).getAllPurchases(null, null)).withRel("purchase_history")

        );
    }
}
