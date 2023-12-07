package com.rewardapp.backend.controllers;

import com.rewardapp.backend.models.Session;
import com.rewardapp.backend.models.UserCredentials;
import com.rewardapp.backend.models.UserModel;
import com.rewardapp.backend.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login")
    public ResponseEntity<RepresentationModel<Session>> login(@RequestBody UserCredentials credentials, HttpServletResponse httpResponse) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(authService.login(credentials, httpResponse));
    }

    @PostMapping("/register")
    public ResponseEntity<RepresentationModel<UserModel>> register(@RequestBody UserCredentials credentials) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(credentials));
    }

    @PatchMapping("/verify/{email_token}")
    public ResponseEntity<RepresentationModel<UserModel>> verifyEmail(@PathVariable("email_token") String token) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.verifyEmail(token));
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
