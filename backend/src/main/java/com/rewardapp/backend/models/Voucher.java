package com.rewardapp.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Voucher extends RepresentationModel<Voucher> {
    private final Long id;
    private final Long userId;
    private final Long purchaseId;
    private String expirationDate;
    private Integer value;
    private String currency;
    private Boolean used;
    private Boolean active;
}
