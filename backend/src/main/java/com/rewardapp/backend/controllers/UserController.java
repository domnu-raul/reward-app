package com.rewardapp.backend.controllers;

import com.rewardapp.backend.models.User;
import com.rewardapp.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserRepository rep;
    @PostMapping
    public Integer register(@RequestBody User user) {
        return rep.create(user.getUsername(), user.getEmail(), user.getPassword());
    }

    @GetMapping("/{id}")
    public EntityModel<User> get_by_id(@PathVariable("id") Integer id) {
        User u = rep.findById(id);
        Link link = linkTo(methodOn(UserController.class).get_by_id(id)).withSelfRel();
        return EntityModel.of(u, link);
    }
}
