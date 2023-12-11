package com.rewardapp.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Purchase extends RepresentationModel<Purchase> {

    private Long id;
    private Long userId;
    private Long purchaseOptionId;
    private String timestamp;
    private Long cost;
}
