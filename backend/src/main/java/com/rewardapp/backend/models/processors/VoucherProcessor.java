package com.rewardapp.backend.models.processors;

import com.rewardapp.backend.controllers.VoucherController;
import com.rewardapp.backend.models.Voucher;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VoucherProcessor implements RepresentationModelProcessor<Voucher> {
    @Override
    public Voucher process(Voucher model) {
        return model.add(
                linkTo(methodOn(VoucherController.class).getAllVouchers(null, null))
                        .withRel("vouchers"),
                linkTo(methodOn(VoucherController.class).getActiveVouchers(null, null))
                        .withRel("active_vouchers"),
                linkTo(methodOn(VoucherController.class).getExpiredVouchers(null, null))
                        .withRel("expired_vouchers")
        );
    }
}
