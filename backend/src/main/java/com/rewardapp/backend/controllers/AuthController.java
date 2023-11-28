package com.rewardapp.backend.controllers;

import com.rewardapp.backend.entities.Session;
import com.rewardapp.backend.entities.User;
import com.rewardapp.backend.services.EmailSenderService;
import com.rewardapp.backend.services.SessionService;
import com.rewardapp.backend.services.UserService;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final UserService userService;
    private final SessionService sessionService;
    private final EmailSenderService emailSenderService;

    public AuthController(UserService userService, SessionService sessionService, EmailSenderService emailSenderService) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/login")
    public ResponseEntity<EntityModel<Session>> login(@RequestBody Map<String, String> requestBody) {
        String username = requestBody.get("username");
        String password = requestBody.get("password");

        User user = userService.validate(username, password);
        Session session = sessionService.create(user);

        EntityModel<Session> response = EntityModel.of(session);

//        Cookie cookie = new Cookie("session_id", session.getSession_id());
//        httpResponse.addCookie(cookie);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<EntityModel<User>> register(@RequestBody Map<String, String> requestBody) {
        String username = requestBody.get("username");
        String email = requestBody.get("email");
        String password = requestBody.get("password");

        User user = userService.register(username, email, password);
        EntityModel<User> response = EntityModel.of(user);

//        emailSenderService.sendEmail("sir.c4ppuccin0@gmail.com", "test", "working");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
