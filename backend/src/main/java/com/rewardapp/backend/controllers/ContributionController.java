package com.rewardapp.backend.controllers;

import com.rewardapp.backend.models.Contribution;
import com.rewardapp.backend.models.ContributionDetails;
import com.rewardapp.backend.models.Session;
import com.rewardapp.backend.services.AuthService;
import com.rewardapp.backend.services.ContributionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contributions")
@RequiredArgsConstructor
public class ContributionController {
    private final AuthService authService;
    private final ContributionService contributionService;

    //todo: add links to user
    @GetMapping("/{id}")
    public ResponseEntity<ContributionDetails> get(@PathVariable("id") Long id, HttpServletRequest request) {
        Session session = authService.validateRequest(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contributionService.get(id));
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<Contribution>> getAll(
            HttpServletRequest request,
            @RequestParam(value = "page", defaultValue = "0") Integer page) {
        Session session = authService.validateRequest(request);

        List<Contribution> contributions = contributionService.getContributionByUserId(session.getUserId(), page);

        return ResponseEntity
                .ok(CollectionModel.of(contributions));
    }

    @PostMapping()
    public ResponseEntity<Contribution> post(@RequestBody Contribution requestBody, HttpServletRequest request) {
        Session session = authService.validateRequest(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(contributionService.save(requestBody, session));
    }

}
