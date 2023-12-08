package com.rewardapp.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailToken {
    private Long id;
    private String token;
    private Date expirationDate;
    private Long userId;
}