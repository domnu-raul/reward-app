package com.rewardapp.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Material {
    private Long id;
    private String name;
}
