package com.rewardapp.backend.controllers;

import com.rewardapp.backend.dao.UserDataDAO;
import com.rewardapp.backend.models.UserData;
import com.rewardapp.backend.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userdata")
@RequiredArgsConstructor
public class UserDataController {
    private final UserDataDAO userDataDAO;
    private final AuthService authService;

    @GetMapping("/{id}")
    public ResponseEntity<UserData> getUserDataById(@PathVariable Long id, HttpServletRequest request) {
        authService.validateAdminRequest(request);

        return ResponseEntity
                .status(200)
                .body(userDataDAO.getUserData(id));
    }

    @GetMapping()
    public ResponseEntity<UserData> getUserData(HttpServletRequest request) {
        return ResponseEntity
                .status(200)
                .body(userDataDAO.getUserData(authService.validateRequest(request).getUserId()));
    }
}
