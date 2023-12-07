package com.rewardapp.backend.controllers;

import com.rewardapp.backend.entities.Session;
import com.rewardapp.backend.models.RecyclingCenterModel;
import com.rewardapp.backend.services.RecyclingCenterService;
import com.rewardapp.backend.services.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
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
    private final SessionService sessionService;

    @GetMapping("/{id}")
    public ResponseEntity<RepresentationModel> get(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        Session session = sessionService.validateRequest(request);
        RecyclingCenterModel model = recyclingCenterService.findRecyclingCenterById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EntityModel.of(model));
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel> getAll(HttpServletRequest request) {
        Session session = sessionService.validateRequest(request);

        List<RecyclingCenterModel> recyclingCenters = recyclingCenterService.getAll();

        CollectionModel<EntityModel<RecyclingCenterModel>> collectionModel = CollectionModel.wrap(recyclingCenters);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    @PostMapping
    public ResponseEntity<RepresentationModel> create(@RequestBody RecyclingCenterModel recyclingCenterModel, HttpServletRequest request) {
        Session session = sessionService.validateAdminRequest(request);

        recyclingCenterModel = recyclingCenterService.create(recyclingCenterModel);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recyclingCenterModel);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpEntity<Boolean>> delete(@PathVariable("id") Long id, HttpServletRequest request) {
        Session session = sessionService.validateAdminRequest(request);

        Boolean status = recyclingCenterService.deleteById(id);
        HttpEntity<Boolean> httpEntity = new HttpEntity<>(status);

        return ResponseEntity
                .status(status ? HttpStatus.OK : HttpStatus.CONFLICT)
                .body(httpEntity);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RepresentationModel> update(@PathVariable("id") Long id, @RequestBody RecyclingCenterModel recyclingCenterModel, HttpServletRequest request) {
        Session session = sessionService.validateAdminRequest(request);

        recyclingCenterModel = recyclingCenterService.update(id, recyclingCenterModel);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EntityModel.of(recyclingCenterModel));
    }
}
