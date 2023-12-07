package com.rewardapp.backend.entities;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class EmailToken {
    private Long id;
    private String token;
    private Date expirationDate;
    private Long userId;
}