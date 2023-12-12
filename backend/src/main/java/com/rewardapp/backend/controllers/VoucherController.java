package com.rewardapp.backend.controllers;

import com.rewardapp.backend.models.Session;
import com.rewardapp.backend.models.Voucher;
import com.rewardapp.backend.services.AuthService;
import com.rewardapp.backend.services.VoucherService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/voucher")
public class VoucherController {
    private final VoucherService voucherService;
    private final AuthService authService;

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<Voucher>> getAllVouchers(HttpServletRequest request,
                                                                   @RequestParam(required = false, defaultValue = "0") Integer page) {

        Session session = authService.validateRequest(request);
        List<Voucher> vouchers = voucherService.getVouchersByUserId(session.getUserId(), page);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CollectionModel.of(vouchers));
    }

    @GetMapping("/active")
    public ResponseEntity<CollectionModel<Voucher>> getActiveVouchers(HttpServletRequest request,
                                                                      @RequestParam(required = false, defaultValue = "0") Integer page) {

        Session session = authService.validateRequest(request);
        List<Voucher> vouchers = voucherService.getActiveVouchersByUserId(session.getUserId(), page);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CollectionModel.of(vouchers));
    }

    @GetMapping("/expired")
    public ResponseEntity<CollectionModel<Voucher>> getExpiredVouchers(HttpServletRequest request,
                                                                       @RequestParam(required = false, defaultValue = "0") Integer page) {

        Session session = authService.validateRequest(request);
        List<Voucher> vouchers = voucherService.getExpiredVouchersByUserId(session.getUserId(), page);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CollectionModel.of(vouchers));
    }

    @GetMapping()
    public ResponseEntity<Voucher> getVoucherByPurchaseId(HttpServletRequest request,
                                                          @RequestParam Long purchaseId) {

        Session session = authService.validateRequest(request);
        Voucher voucher = voucherService.getVoucherByPurchaseId(session.getUserId(), purchaseId);


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(voucher);
    }


}
