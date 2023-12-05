package com.rewardapp.backend.controllers;

import com.rewardapp.backend.entities.RecyclingCenter;
import com.rewardapp.backend.entities.Session;
import com.rewardapp.backend.entities.dto.RecyclingCenterDTO;
import com.rewardapp.backend.services.RecyclingCenterService;
import com.rewardapp.backend.services.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/recycling-centers")
public class RecyclingCenterController {
    private final RecyclingCenterService recyclingCenterService;
    private final SessionService sessionService;

    public RecyclingCenterController(RecyclingCenterService recyclingCenterService, SessionService sessionService) {
        this.recyclingCenterService = recyclingCenterService;
        this.sessionService = sessionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<RecyclingCenterDTO>> get(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        Session session = sessionService.validateRequest(request);

        RecyclingCenterDTO recyclingCenterDTO = recyclingCenterService.getRecyclingCenterDTOById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EntityModel.of(recyclingCenterDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<RecyclingCenterDTO>> getAll(HttpServletRequest request) {
        Session session = sessionService.validateRequest(request);

        List<RecyclingCenterDTO> recyclingCenters = recyclingCenterService.getAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CollectionModel.of(recyclingCenters));
    }

    @PostMapping()
    public ResponseEntity<EntityModel<RecyclingCenterDTO>> create(@RequestBody RecyclingCenterDTO recyclingCenterDTO, HttpServletRequest request) {
        Session session = sessionService.validateAdminRequest(request);

        recyclingCenterDTO = recyclingCenterService.create(recyclingCenterDTO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EntityModel.of(recyclingCenterDTO));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id, HttpServletRequest request) {
        Session session = sessionService.validateAdminRequest(request);

        RecyclingCenter recyclingCenter = recyclingCenterService.getRecyclingCenterById(id);

        recyclingCenterService.delete(recyclingCenter);

        return null;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<RecyclingCenterDTO>> update(@PathVariable("id") Long id, @RequestBody RecyclingCenterDTO recyclingCenterDTO, HttpServletRequest request) {
        Session session = sessionService.validateAdminRequest(request);

        recyclingCenterDTO = recyclingCenterService.update(id, recyclingCenterDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EntityModel.of(recyclingCenterDTO));
    }
}
