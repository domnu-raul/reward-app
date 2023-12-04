package com.rewardapp.backend.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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
    @Column(value = "id")
    private Material material;
    @Column("timestamp")
    private Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
    @Column("quantity")
    private Double quantity;
    @Column("measurment")
    private MeasurmentType measurment;
    @Column("reward")
    private Long reward;

    private enum MeasurmentType {
        PIECE, KG;
    }
}