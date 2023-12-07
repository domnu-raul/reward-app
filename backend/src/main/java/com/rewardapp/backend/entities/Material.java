package com.rewardapp.backend.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Material {
    private Long id;
    private String name;
}
