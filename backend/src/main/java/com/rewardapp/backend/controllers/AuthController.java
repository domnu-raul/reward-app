package com.rewardapp.backend.controllers;

import com.rewardapp.backend.entities.EmailToken;
import com.rewardapp.backend.entities.Session;
import com.rewardapp.backend.entities.User;
import com.rewardapp.backend.entities.dto.UserCredentials;
import com.rewardapp.backend.services.EmailSenderService;
import com.rewardapp.backend.services.EmailTokenService;
import com.rewardapp.backend.services.SessionService;
import com.rewardapp.backend.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final UserService userService;
    private final SessionService sessionService;
    private final EmailSenderService emailSenderService;
    private final EmailTokenService emailTokenService;

    public AuthController(UserService userService, SessionService sessionService, EmailSenderService emailSenderService, EmailTokenService emailTokenService) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.emailSenderService = emailSenderService;
        this.emailTokenService = emailTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<EntityModel<Session>> login(@RequestBody UserCredentials credentials, HttpServletResponse httpResponse) {
        User user = userService.validateInternal(credentials);

        Session session = sessionService.create(user);

        Cookie cookie = new Cookie("session_id", session.getSessionId());
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        httpResponse.addCookie(cookie);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(EntityModel.of(session));
    }

    @PostMapping("/register")
    public ResponseEntity<EntityModel<User>> register(@RequestBody UserCredentials credentials) {
        User user = userService.registerInternal(credentials);
        EmailToken emailToken = emailTokenService.create(user.getId());
        System.out.println(emailToken.getToken());
//        emailSenderService.sendEmail("sir.c4ppuccin0@gmail.com", "test", "working");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(EntityModel.of(user));
    }

    @PatchMapping("/verify/{email_token}")
    public ResponseEntity<EntityModel<User>> verifyEmail(@PathVariable("email_token") String token) {
        EmailToken emailToken = emailTokenService.findEmailByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email token."));

        Long userId = emailToken.getUserId();

        User user = userService.setVerified(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EntityModel.of(user));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        Session session = sessionService.validateRequest(request);
        if (session == null)
            throw new RuntimeException("You are not logged in.");

        sessionService.logout(session.getSessionId());

        return null;
    }

    @GetMapping("/ping")
    public ResponseEntity<?> ping(HttpServletRequest request) {
        Session session = sessionService.validateRequest(request);
        return ResponseEntity
                .status(session == null ? HttpStatus.UNAUTHORIZED : HttpStatus.ACCEPTED)
                .body(session == null);
    }
}
