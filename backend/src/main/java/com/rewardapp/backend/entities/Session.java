package com.rewardapp.backend.entities;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class Session {
    private Long id;
    private String sessionId;
    private Date expirationDate;
    private Long userId;
}