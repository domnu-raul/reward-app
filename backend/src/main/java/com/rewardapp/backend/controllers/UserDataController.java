package com.rewardapp.backend.controllers;

import com.rewardapp.backend.entities.Session;
import com.rewardapp.backend.entities.UserData;
import com.rewardapp.backend.repositories.UserDataRepository;
import com.rewardapp.backend.services.SessionService;
import com.rewardapp.backend.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-data")
public class UserDataController {
    private final UserDataRepository repository;
    private final SessionService sessionService;
    public UserDataController(UserDataRepository repository, SessionService sessionService, UserService userService) {
        this.repository = repository;
        this.sessionService = sessionService;
    }

    @GetMapping
    public ResponseEntity<EntityModel<UserData>> get(HttpServletRequest request) {
        Session session = sessionService.validateRequest(request);
        Long userId = session.getUserId();

        EntityModel<UserData> response = EntityModel.of(repository.getUserDataByUserId(userId));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
