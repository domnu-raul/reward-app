package com.rewardapp.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails extends RepresentationModel<UserDetails> {
    private Integer rewardPoints;
    private Integer totalPointsEarned;
    private Integer totalContributions;
    private Integer totalVouchers;
    private Integer usedVouchers;
    private Integer moneyEarned;
}
