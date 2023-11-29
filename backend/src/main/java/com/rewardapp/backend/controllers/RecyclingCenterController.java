package com.rewardapp.backend.controllers;

import com.rewardapp.backend.entities.RecyclingCenter;
import com.rewardapp.backend.repositories.RecyclingCenterRepository;
import com.rewardapp.backend.services.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("api/recycling-centers")
public class RecyclingCenterController {
    private final RecyclingCenterRepository recyclingCenterRepository;
    private final SessionService sessionService;

    public RecyclingCenterController(RecyclingCenterRepository recyclingCenterRepository, SessionService sessionService) {
        this.recyclingCenterRepository = recyclingCenterRepository;
        this.sessionService = sessionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<RecyclingCenter>> get(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            throw new RuntimeException("Empty cookie jar.");

        Optional<String> sessionIdOptional = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("session_id"))
                .map(Cookie::getValue)
                .findAny();

        if (sessionIdOptional.isEmpty())
            throw new RuntimeException("You must be logged in.");

        String sessionId = sessionIdOptional.get();
        if (sessionService.validate(sessionId) == null)
            throw new RuntimeException("Session expired.");

        RecyclingCenter r = recyclingCenterRepository.getRecyclingCenterById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EntityModel.of(r));
    }

}
