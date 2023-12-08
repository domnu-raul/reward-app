package com.rewardapp.backend.controllers;

import com.rewardapp.backend.models.RecyclingCenter;
import com.rewardapp.backend.services.AuthService;
import com.rewardapp.backend.services.RecyclingCenterService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/recycling-centers")
@RequiredArgsConstructor
public class RecyclingCenterController {
    private final RecyclingCenterService recyclingCenterService;
    private final AuthService authService;

    @GetMapping("/{id}")
    public ResponseEntity<RecyclingCenter> get(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        authService.validateRequest(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recyclingCenterService.findRecyclingCenterById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<RecyclingCenter>> getAll(HttpServletRequest request) {
        authService.validateRequest(request);

        List<RecyclingCenter> recyclingCenters = recyclingCenterService.getAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CollectionModel.of(recyclingCenters));
    }

    @PostMapping
    public ResponseEntity<RecyclingCenter> create(@RequestBody RecyclingCenter recyclingCenter, HttpServletRequest request) {
        authService.validateAdminRequest(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recyclingCenterService.save(recyclingCenter));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RecyclingCenter> update(@PathVariable("id") Long id, @RequestBody RecyclingCenter recyclingCenter, HttpServletRequest request) {
        authService.validateAdminRequest(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recyclingCenterService.update(id, recyclingCenter));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpEntity<Boolean>> delete(@PathVariable("id") Long id, HttpServletRequest request) {
        authService.validateAdminRequest(request);

        Boolean status = recyclingCenterService.deleteById(id);
        HttpEntity<Boolean> httpEntity = new HttpEntity<>(status);

        return ResponseEntity
                .status(status ? HttpStatus.OK : HttpStatus.CONFLICT)
                .body(httpEntity);
    }
}
