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
    public ResponseEntity<RecyclingCenter> get(@PathVariable(name = "id") Long id,
                                               HttpServletRequest request) {
        authService.validateRequest(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(recyclingCenterService.findRecyclingCenterById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<RecyclingCenter>> getAll(HttpServletRequest request,
                                                                   @RequestParam(required = false) List<String> materials,
                                                                   @RequestParam(required = false) String search,
                                                                   @RequestParam(required = false) String order,
                                                                   @RequestParam(required = false) Boolean reverse,
                                                                   @RequestParam(required = false) Boolean open,
                                                                   @RequestParam(required = false) List<Double> latlng,
                                                                   @RequestParam(required = false, defaultValue = "0") Integer page) {
        authService.validateRequest(request);

        List<RecyclingCenter> recyclingCenters;

        if (latlng != null) {
            if (latlng.size() != 2)
                throw new IllegalArgumentException("latlng must be an array of size 2");

            recyclingCenters = recyclingCenterService.getClosest(latlng.get(0), latlng.get(1), page);
        } else
            recyclingCenters = recyclingCenterService.getAll(materials, search, order,
                    reverse, open, page);

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
    public ResponseEntity<RecyclingCenter> update(@PathVariable("id") Long id,
                                                  @RequestBody RecyclingCenter recyclingCenter,
                                                  HttpServletRequest request) {
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
