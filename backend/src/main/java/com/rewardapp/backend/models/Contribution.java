package com.rewardapp.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contribution extends RepresentationModel<Contribution> {
    private Long id;
    private Long userId;
    private Long recyclingCenterId;
    private Long materialId;
    private String timestamp;
    private Double quantity;
    private MeasurementType measurement;
    private Long reward;

    public enum MeasurementType {
        PIECE, KG;
    }
}