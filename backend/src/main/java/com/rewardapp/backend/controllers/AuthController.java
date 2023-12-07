package com.rewardapp.backend.controllers;

import com.rewardapp.backend.entities.Session;
import com.rewardapp.backend.models.UserCredentials;
import com.rewardapp.backend.models.UserModel;
import com.rewardapp.backend.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    //todo: make a DTO for USER and SESSION, and use them instead of the entities
    //todo: the DTO's should extend RepresentationModel
    @GetMapping("/login")
    public ResponseEntity<RepresentationModel> login(@RequestBody UserCredentials credentials, HttpServletResponse httpResponse) {
        Session session = authService.login(credentials);

        Cookie cookie = new Cookie("session_id", session.getSessionId());
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        httpResponse.addCookie(cookie);

        EntityModel<Session> sessionEntityModel = EntityModel.of(session);
        sessionEntityModel.add(linkTo(methodOn(AuthController.class).logout(null)).withRel("logout"));

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(sessionEntityModel);
    }

    @PostMapping("/register")
    public ResponseEntity<RepresentationModel> register(@RequestBody UserCredentials credentials) {
        UserModel userModel = authService.register(credentials);
        EntityModel<UserModel> userEntityModel = EntityModel.of(userModel);

        userEntityModel.add(linkTo(methodOn(AuthController.class).logout(null)).withRel("logout"));
        userEntityModel.add(linkTo(methodOn(AuthController.class).login(null, null)).withRel("login"));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userEntityModel);
    }

    @PatchMapping("/verify/{email_token}")
    public ResponseEntity<RepresentationModel> verifyEmail(@PathVariable("email_token") String token) {
        UserModel userModel = authService.verifyEmail(token);

        EntityModel<UserModel> userEntityModel = EntityModel.of(userModel);
        userEntityModel.add(linkTo(methodOn(AuthController.class).login(null, null)).withRel("login"));
        userEntityModel.add(linkTo(methodOn(AuthController.class).logout(null)).withRel("logout"));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userEntityModel);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        Session session = authService.validateRequest(request);
        authService.logout(session);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
