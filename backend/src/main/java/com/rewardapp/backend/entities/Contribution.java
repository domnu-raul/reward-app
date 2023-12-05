package com.rewardapp.backend.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Table(name = "contributions")
@Data
public class Contribution {
    @Id
    @Column("id")
    private Long id;
    @Column("user_id")
    private Long userId;
    @Column("recycling_center_id")
    private Long recyclingCenterId;
    @Column("material_id")
    private Long materialId;
    @Column("timestamp")
    private Timestamp timestamp;
    @Column("quantity")
    private Double quantity;
    @Column("measurment")
    private MeasurmentType measurment;
    @Column("reward")
    private Long reward;

    public enum MeasurmentType {
        PIECE, KG;
    }
}