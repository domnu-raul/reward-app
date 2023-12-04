package com.rewardapp.backend.controllers;

import com.rewardapp.backend.entities.Contribution;
import com.rewardapp.backend.entities.Session;
import com.rewardapp.backend.repositories.ContributionRepository;
import com.rewardapp.backend.services.SessionService;
import com.rewardapp.backend.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/contributions")
public class ContributionController {
    private final SessionService sessionService;
    private final ContributionRepository contributionRepository;
    public ContributionController(SessionService sessionService, UserService userService, ContributionRepository contributionRepository) {
        this.sessionService = sessionService;
        this.contributionRepository = contributionRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Contribution>> get(@PathVariable("id") Long id, HttpServletRequest request) {
        Session session = sessionService.validateRequest(request);

        Contribution contribution = contributionRepository.getContributionByIdAndUserId(id, session.getUserId());

        EntityModel<Contribution> response = EntityModel.of(contribution);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<Contribution>> getAll(HttpServletRequest request) {
        Session session = sessionService.validateRequest(request);

        List<Contribution> contributions = contributionRepository.getAllByUserId(session.getUserId());

        CollectionModel<Contribution> response = CollectionModel.of(contributions);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
