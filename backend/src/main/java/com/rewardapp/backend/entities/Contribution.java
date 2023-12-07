package com.rewardapp.backend.entities;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class Contribution {
    private Long id;
    private Long userId;
    private Long recyclingCenterId;
    private Long materialId;
    private Timestamp timestamp;
    private Double quantity;
    private MeasurementType measurement;
    private Long reward;

    public enum MeasurementType {
        PIECE, KG;
    }
}