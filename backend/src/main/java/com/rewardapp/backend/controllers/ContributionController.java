package com.rewardapp.backend.controllers;

import com.rewardapp.backend.dao.ContributionDAO;
import com.rewardapp.backend.dao.ContributionModelDAO;
import com.rewardapp.backend.entities.Contribution;
import com.rewardapp.backend.entities.Session;
import com.rewardapp.backend.models.ContributionModel;
import com.rewardapp.backend.services.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/contributions")
@RequiredArgsConstructor
public class ContributionController {
    private final SessionService sessionService;
    private final ContributionDAO contributionDAO;
    private final ContributionModelDAO contributionModelDAO;

    //todo: make DTO for Contribution extending RepresentationModel
    //todo: add links to user and recycling center
    //todo: add link to self
    //todo: add link to all contributions
    //todo: make detailed DTO for Contribution with a DAO method that returns it
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ContributionModel>> get(@PathVariable("id") Long id, HttpServletRequest request) {
        Session session = sessionService.validateRequest(request);

        ContributionModel contribution = contributionModelDAO.getContributionDetails(id);
        EntityModel<ContributionModel> response = EntityModel.of(contribution);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel> getAll(HttpServletRequest request) {
        Session session = sessionService.validateRequest(request);

        List<Contribution> contributions = contributionDAO.getContributionsByUserId(session.getUserId());
        List<EntityModel<Contribution>> entities = contributions.stream()
                .map(contribution -> EntityModel.of(contribution))
                .peek(entity -> {
                    entity.add(linkTo(methodOn(ContributionController.class).get(entity.getContent().getId(), request)).withRel("contribution"));
//                    entity.add(linkTo(methodOn(UserController.class).get(entity.getContent().getUserId(), request)).withRel("user"));
                    //todo: add link to user
                    //todo: add link to recycling center
                    entity.add(linkTo(methodOn(RecyclingCenterController.class).get(entity.getContent().getRecyclingCenterId(), request)).withRel("recycling_center"));
                })
                .toList();

        CollectionModel<EntityModel<Contribution>> response = CollectionModel.of(entities);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
