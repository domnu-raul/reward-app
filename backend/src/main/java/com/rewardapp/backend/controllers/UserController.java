package com.rewardapp.backend.controllers;

import com.rewardapp.backend.models.UserModel;
import com.rewardapp.backend.services.UserService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<EntityModel<UserModel>> register(@RequestBody UserModel user) {
        EntityModel<UserModel> response = EntityModel.of(service.register(user));

        Link link = linkTo(methodOn(UserController.class).find_by_id(user.getId())).withSelfRel();
        response.add(link);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<EntityModel<UserModel>> find_by_id(@PathVariable("id") Integer id) {
        UserModel user = service.find_by_id(id);
        Link link = linkTo(methodOn(UserController.class).find_by_id(id)).withSelfRel();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EntityModel.of(user, link));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<EntityModel<UserModel>> find_by_username(@PathVariable("username") String username) {
        UserModel user = service.find_by_username(username);
        Link link = linkTo(methodOn(UserController.class).find_by_id(user.getId())).withSelfRel();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EntityModel.of(user, link));

    }
}
