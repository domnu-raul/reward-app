package com.rewardapp.backend.models.processors;

import com.rewardapp.backend.controllers.ContributionController;
import com.rewardapp.backend.controllers.PurchaseController;
import com.rewardapp.backend.controllers.VoucherController;
import com.rewardapp.backend.models.UserDetails;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserDetailsProcessor implements RepresentationModelProcessor<UserDetails> {
    @Override
    public UserDetails process(UserDetails model) {
        return model.add(
                linkTo(methodOn(ContributionController.class).getAll(null, null))
                        .withRel("contributions"),
                linkTo(methodOn(PurchaseController.class).getAllPurchases(null, null))
                        .withRel("purchase_history"),
                linkTo(methodOn(PurchaseController.class).getOptions(null))
                        .withRel("purchase_options"),
                linkTo(methodOn(PurchaseController.class).createPurchase(null, null))
                        .withRel("create_purchase"),
                linkTo(methodOn(VoucherController.class).getAllVouchers(null, null))
                        .withRel("vouchers"),
                linkTo(methodOn(VoucherController.class).getActiveVouchers(null, null))
                        .withRel("active_vouchers"),
                linkTo(methodOn(VoucherController.class).getExpiredVouchers(null, null))
                        .withRel("expired_vouchers")
        );
    }
}
