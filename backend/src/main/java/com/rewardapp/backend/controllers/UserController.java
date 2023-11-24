package com.rewardapp.backend.controllers;

import com.rewardapp.backend.models.User;
import com.rewardapp.backend.services.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EntityModel<User>> register(@RequestBody User user) {
        EntityModel<User> response = EntityModel.of(service.register(user));

        Link link = linkTo(methodOn(UserController.class).find_by_id(user.getId())).withSelfRel();
        response.add(link);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public EntityModel<User> find_by_id(@PathVariable("id") Integer id) {
        User user = service.find_by_id(id);
        Link link = linkTo(methodOn(UserController.class).find_by_id(id)).withSelfRel();
        return EntityModel.of(user, link);
    }
}
