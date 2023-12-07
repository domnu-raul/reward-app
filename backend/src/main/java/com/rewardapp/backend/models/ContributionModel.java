package com.rewardapp.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContributionModel extends RepresentationModel<ContributionModel> {
    private Long id;
    private UserModel userModel;
    private RecyclingCenterModel recyclingCenter;
    private String material;
    private String timestamp;
    private Double quantity;
    private String measurement;
    private Long reward;
}
