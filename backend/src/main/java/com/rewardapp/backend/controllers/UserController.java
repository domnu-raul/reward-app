package com.rewardapp.backend.controllers;

import com.rewardapp.backend.models.User;
import com.rewardapp.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserRepository rep;
    @PostMapping
    public Integer add_user(@RequestBody User user) {
        return rep.create(user.getUsername(), user.getEmail(), user.getPassword());
    }

    @GetMapping("/{id}")
    public User get_by_id(@PathVariable("id") Integer id) {
        return rep.findById(id);
    }
}
