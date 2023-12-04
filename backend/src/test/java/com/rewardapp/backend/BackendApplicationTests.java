package com.rewardapp.backend;

import com.rewardapp.backend.entities.Material;
import com.rewardapp.backend.repositories.ContributionRepository;
import com.rewardapp.backend.repositories.MaterialRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {
    @Autowired
    ContributionRepository contributionRepository;
    @Autowired
    MaterialRepository materialRepository;

    @Test
    void setContributionRepository() {
        contributionRepository.getAllByUserId(102l)
                .stream()
                .forEach(x -> {
                    System.out.println(
                            (Material) x.getMaterial()
                    );
                });
    }
}
