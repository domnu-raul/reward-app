package com.rewardapp.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContributionDetails extends RepresentationModel<ContributionDetails> {
    private Long id;
    private User user;
    private RecyclingCenter recyclingCenter;
    private String material;
    private String timestamp;
    private Double quantity;
    private String measurement;
    private Long reward;
}
