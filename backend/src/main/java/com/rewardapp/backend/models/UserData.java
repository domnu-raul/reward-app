package com.rewardapp.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData extends RepresentationModel<UserData> {
    private User user;
    private UserStatistics userStatistics;
    private List<Contribution> latestContributions;
    private List<Voucher> activeVouchers;
    private List<Voucher> usedVouchers;
}
