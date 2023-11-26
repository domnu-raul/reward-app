package com.rewardapp.backend.controllers;

import com.rewardapp.backend.models.InternalUser;
import com.rewardapp.backend.models.Session;
import com.rewardapp.backend.models.User;
import com.rewardapp.backend.models.UserResponse;
import com.rewardapp.backend.repositories.AuthRepository;
import com.rewardapp.backend.services.AuthService;
import com.rewardapp.backend.services.SessionService;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService auth_service;
    private final SessionService session_service;
    private final AuthRepository auth_repository;

    public AuthController(AuthService auth_service, SessionService session_service, AuthRepository authRepository) {
        this.auth_service = auth_service;
        this.session_service = session_service;
        this.auth_repository = authRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<EntityModel<Session>> login(@RequestBody InternalUser user) {
        Session session = auth_service.login_validation(user);
        EntityModel<Session> response = EntityModel.of(session);

//        Cookie cookie = new Cookie("session_id", session.getSession_id());
//        httpResponse.addCookie(cookie);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody InternalUser user) {
        User new_user = this.auth_service.register(user);

        UserResponse response = UserResponse.fromUser(new_user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
