package com.rewardapp.backend.controllers;

import com.rewardapp.backend.entities.Purchase;
import com.rewardapp.backend.entities.Session;
import com.rewardapp.backend.repositories.PurchaseRepository;
import com.rewardapp.backend.services.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
public class PurchaseController {
    private final SessionService sessionService;
    private final PurchaseRepository purchaseRepository;

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Purchase>> get(@PathVariable("id") Long id, HttpServletRequest request) {
        Session session = sessionService.validateRequest(request);

        Purchase purchase = purchaseRepository.getPurchaseByIdAndUserId(id, session.getUserId());

        EntityModel<Purchase> response = EntityModel.of(purchase);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<Purchase>> getAll(HttpServletRequest request) {
        Session session = sessionService.validateRequest(request);

        List<Purchase> purchases = purchaseRepository.getAllByUserId(session.getUserId());

        CollectionModel<Purchase> response = CollectionModel.of(purchases);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
