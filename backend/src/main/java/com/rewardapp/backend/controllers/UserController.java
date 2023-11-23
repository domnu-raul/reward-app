package com.rewardapp.backend.controllers;

import com.rewardapp.backend.models.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @PostMapping
    public String add_user(@RequestBody User user) {
        return user.toString();
    }
}
