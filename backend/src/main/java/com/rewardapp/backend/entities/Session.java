package com.rewardapp.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Table("sessions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    @Id
    @Column("id")
    private Long id;
    @Column("session_id")
    private String sessionId = UUID.randomUUID().toString();
    @Column("expiration_date")
    private Date expirationDate = Date.valueOf(LocalDate.now().plusDays(3L));
    @Column("user_id")
    private Long userId;
}