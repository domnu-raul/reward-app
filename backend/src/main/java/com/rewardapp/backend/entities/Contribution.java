package com.rewardapp.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
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