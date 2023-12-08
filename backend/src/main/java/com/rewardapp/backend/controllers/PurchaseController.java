package com.rewardapp.backend.controllers;

import com.rewardapp.backend.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
public class PurchaseController {
    private final AuthService authService;
    //todo: add 'all' endpoint
    //todo: add 'get' endpoint
    //todo: add 'post' endpoint
    //todo: add 'options' endpoint
    //todo: add 'options/post' endpoint
//    private final PurchaseRepository purchaseRepository;
//
//    @GetMapping("/{id}")
//    public ResponseEntity<EntityModel<Purchase>> get(@PathVariable("id") Long id, HttpServletRequest request) {
//        Session session = sessionService.validateRequest(request);
//
//        Purchase purchase = purchaseRepository.getPurchaseByIdAndUserId(id, session.getUserId());
//
//        EntityModel<Purchase> response = EntityModel.of(purchase);
//
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(response);
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<CollectionModel<Purchase>> getAll(HttpServletRequest request) {
//        Session session = sessionService.validateRequest(request);
//
//        List<Purchase> purchases = purchaseRepository.getAllByUserId(session.getUserId());
//
//        CollectionModel<Purchase> response = CollectionModel.of(purchases);
//
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(response);
//    }
}
