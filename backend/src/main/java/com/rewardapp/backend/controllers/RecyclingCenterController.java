package com.rewardapp.backend.controllers;

import com.rewardapp.backend.entities.RecyclingCenter;
import com.rewardapp.backend.entities.Session;
import com.rewardapp.backend.services.RecyclingCenterService;
import com.rewardapp.backend.services.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/recycling-centers")
public class RecyclingCenterController {
    private final RecyclingCenterService recyclingCenterService;
    private final SessionService sessionService;


    public RecyclingCenterController(RecyclingCenterService recyclingCenterService, SessionService sessionService) {
        this.recyclingCenterService = recyclingCenterService;
        this.sessionService = sessionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<RecyclingCenter>> get(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        Session session = sessionService.validateRequest(request);

        RecyclingCenter recyclingCenter = recyclingCenterService.getRecyclingCenterById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EntityModel.of(recyclingCenter));
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<RecyclingCenter>> getAll(HttpServletRequest request) {
        Session session = sessionService.validateRequest(request);

        List<RecyclingCenter> recyclingCenters = recyclingCenterService.getAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CollectionModel.of(recyclingCenters));
    }
    @PostMapping()
    public ResponseEntity<EntityModel<RecyclingCenter>> create(@RequestBody RecyclingCenter recyclingCenter, HttpServletRequest request) {
        Session session = sessionService.validateAdminRequest(request);

        recyclingCenter = recyclingCenterService.create(recyclingCenter);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EntityModel.of(recyclingCenter));
    }



}
