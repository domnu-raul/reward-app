package com.rewardapp.backend.controllers;

import com.rewardapp.backend.dao.UserDetailsDAO;
import com.rewardapp.backend.models.UserDetails;
import com.rewardapp.backend.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-details")
@RequiredArgsConstructor
public class UserDataController {
    private final UserDetailsDAO userDetailsDAO;
    private final AuthService authService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDetails> getUserDataById(@PathVariable Long id, HttpServletRequest request) {
        authService.validateAdminRequest(request);

        return ResponseEntity
                .status(200)
                .body(userDetailsDAO.getUserData(id));
    }

    @GetMapping()
    public ResponseEntity<UserDetails> getUserData(HttpServletRequest request) {
        return ResponseEntity
                .status(200)
                .body(userDetailsDAO.getUserData(authService.validateRequest(request).getUserId()));
    }
}
