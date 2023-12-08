package com.rewardapp.backend.controllers;

import com.rewardapp.backend.dao.ContributionDAO;
import com.rewardapp.backend.dao.ContributionDetailsDAO;
import com.rewardapp.backend.models.Contribution;
import com.rewardapp.backend.models.ContributionDetails;
import com.rewardapp.backend.models.Session;
import com.rewardapp.backend.services.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/contributions")
@RequiredArgsConstructor
public class ContributionController {
    private final SessionService sessionService;
    private final ContributionDAO contributionDAO;
    private final ContributionDetailsDAO contributionDetailsDAO;

    //todo: add links to user
    @GetMapping("/{id}")
    public ResponseEntity<RepresentationModel<ContributionDetails>> get(@PathVariable("id") Long id, HttpServletRequest request) {
        sessionService.validateRequest(request);

        ContributionDetails contribution = contributionDetailsDAO.getContributionDetails(id)
                .orElseThrow(() -> new RuntimeException("fuck"));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contribution);
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<Contribution>> getAll(HttpServletRequest request) {
        Session session = sessionService.validateRequest(request);

        List<Contribution> contributions = contributionDAO.getContributionsByUserId(session.getUserId());
        CollectionModel<Contribution> response = CollectionModel.of(contributions);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
