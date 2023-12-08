package com.rewardapp.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatistics {
    private Integer rewardPoints;
    private Integer currentPoints;
    private Integer totalPointsEarned;
    private Integer totalContributions;
    private Integer totalVouchers;
    private Integer usedVouchers;
    private Integer moneyEarned;
}
