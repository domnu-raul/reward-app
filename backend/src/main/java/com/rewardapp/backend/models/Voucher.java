package com.rewardapp.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voucher extends RepresentationModel<Voucher> {
    private Long id;
    private Long userId;
    private Long purchaseId;
    private String expirationDate;
    private Integer value;
    private String currency;
    private Boolean used;
    private Boolean active;
}
