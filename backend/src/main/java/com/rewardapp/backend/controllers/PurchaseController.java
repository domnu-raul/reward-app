package com.rewardapp.backend.controllers;

import com.rewardapp.backend.models.Purchase;
import com.rewardapp.backend.models.PurchaseOption;
import com.rewardapp.backend.models.Session;
import com.rewardapp.backend.services.AuthService;
import com.rewardapp.backend.services.PurchaseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
public class PurchaseController {
    private final AuthService authService;
    private final PurchaseService purchaseService;
    //todo: add 'all' endpoint
    //todo: add 'get' endpoint
    //todo: add 'post' endpoint
    //todo: add 'options' endpoint
    //todo: add 'options/post' endpoint

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<Purchase>> getAllPurchases(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer page) {
        Session session = authService.validateRequest(request);
        List<Purchase> purchases = purchaseService.getPurchasesByUserId(session.getUserId(), page);

        return ResponseEntity
                .ok(CollectionModel.of(purchases));
    }

    @GetMapping("/options")
    public ResponseEntity<CollectionModel<PurchaseOption>> getOptions(HttpServletRequest request) {
        authService.validateRequest(request);
        List<PurchaseOption> purchaseOptions = purchaseService.getOptions();

        return ResponseEntity
                .ok(CollectionModel.of(purchaseOptions));
    }

    @PostMapping("/{optionId}")
    public ResponseEntity<Purchase> createPurchase(HttpServletRequest request, @PathVariable Long optionId) {
        Session session = authService.validateRequest(request);
        Purchase purchase = purchaseService.createPurchase(session.getUserId(), optionId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(purchase);
    }
}
