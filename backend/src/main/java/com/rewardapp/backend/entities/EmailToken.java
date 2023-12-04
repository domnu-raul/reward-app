package com.rewardapp.backend.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Table("email_tokens")
@Data
public class EmailToken {
    @Id
    @Column("id")
    private Long id;
    @Column("token")
    private String token = UUID.randomUUID().toString();
    @Column("expiration_date")
    private Date expirationDate = Date.valueOf(LocalDate.now().plusDays(30L));
    @Column("user_id")
    private Long userId;
}