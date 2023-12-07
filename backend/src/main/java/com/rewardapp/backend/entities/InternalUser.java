package com.rewardapp.backend.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InternalUser {
    private Long id;
    private String password;
}
