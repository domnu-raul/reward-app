package com.rewardapp.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session extends RepresentationModel<Session> {
    private Long id;
    private String sessionId;
    private Date expirationDate;
    private Long userId;
}