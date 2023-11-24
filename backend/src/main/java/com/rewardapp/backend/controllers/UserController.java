package com.rewardapp.backend.controllers;

import com.rewardapp.backend.models.User;
import com.rewardapp.backend.services.UserService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
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

    @PostMapping
    public Integer register(@RequestBody User user) {
        return service.register(user.getUsername(), user.getEmail(), user.getPassword());
    }

    @GetMapping("/{id}")
    public EntityModel<User> find_by_id(@PathVariable("id") Integer id) {
        User user = service.find_by_id(id);
        Link link = linkTo(methodOn(UserController.class).find_by_id(id)).withSelfRel();
        return EntityModel.of(user, link);
    }
}
