package com.rewardapp.backend.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Table("purchases")
@Data
public class Purchase {

    @Id
    @Column("id")
    private Long id;

    @Column("user_id")
    private Long userId;

    @Column("timestamp")
    private Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

    @Column("cost")
    private Long cost;
}
