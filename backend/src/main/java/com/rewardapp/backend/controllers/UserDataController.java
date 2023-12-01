package com.rewardapp.backend.controllers;

import com.rewardapp.backend.entities.UserData;
import com.rewardapp.backend.repositories.UserDataRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-data")
public class UserDataController {
    private final UserDataRepository repository;
    public UserDataController(UserDataRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserData>> get(@PathVariable("id") Long id) {
        EntityModel<UserData> response = EntityModel.of(repository.getUserDataByUserId(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
