package com.rewardapp.backend.controllers;

import com.rewardapp.backend.entities.RecyclingCenter;
import com.rewardapp.backend.repositories.RecyclingCenterRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/recycling-centers")
public class RecyclingCenterController {
    private final RecyclingCenterRepository repository;

    public RecyclingCenterController(RecyclingCenterRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<RecyclingCenter>> get(@PathVariable(name = "id") Long id) {
        RecyclingCenter r = repository.getRecyclingCenterById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EntityModel.of(r));
    }

}
