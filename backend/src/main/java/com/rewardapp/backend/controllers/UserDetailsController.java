package com.rewardapp.backend.controllers;

import com.rewardapp.backend.dao.UserDetailsDAO;
import com.rewardapp.backend.models.Session;
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
public class UserDetailsController {
    private final UserDetailsDAO userDetailsDAO;
    private final AuthService authService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDetails> getUserDataById(@PathVariable Long id, HttpServletRequest request) {
        authService.validateAdminRequest(request);

        return ResponseEntity
                .ok(userDetailsDAO.getUserDetails(id));
    }

    @GetMapping()
    public ResponseEntity<UserDetails> getUserData(HttpServletRequest request) {
        Session session = authService.validateRequest(request);

        return ResponseEntity
                .ok(userDetailsDAO.getUserDetails(session.getUserId()));
    }
}
