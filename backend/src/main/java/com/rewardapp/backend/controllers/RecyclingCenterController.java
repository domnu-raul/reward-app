package com.rewardapp.backend.controllers;

import com.rewardapp.backend.entities.RecyclingCenter;
import com.rewardapp.backend.entities.Session;
import com.rewardapp.backend.repositories.RecyclingCenterRepository;
import com.rewardapp.backend.services.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/recycling-centers")
public class RecyclingCenterController {
    private final RecyclingCenterRepository recyclingCenterRepository;
    private final SessionService sessionService;

    public RecyclingCenterController(RecyclingCenterRepository recyclingCenterRepository, SessionService sessionService) {
        this.recyclingCenterRepository = recyclingCenterRepository;
        this.sessionService = sessionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<RecyclingCenter>> get(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        Session session = sessionService.validateRequest(request);
        if (session == null)
            throw new RuntimeException("You must be logged in to access data.");

        RecyclingCenter r = recyclingCenterRepository.getRecyclingCenterById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EntityModel.of(r));
    }

    @PostMapping()
    public ResponseEntity<EntityModel<RecyclingCenter>> create(@RequestBody RecyclingCenter recyclingCenter, HttpServletRequest request) {
        Session session = sessionService.validateRequest(request);
        if (session == null)
            throw new RuntimeException("You must be logged in to access data.");

        RecyclingCenter out = recyclingCenterRepository.save(recyclingCenter);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EntityModel.of(out));
    }



}
