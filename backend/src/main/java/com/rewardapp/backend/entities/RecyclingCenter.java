package com.rewardapp.backend.entities;

import lombok.Builder;
import lombok.Data;

import java.sql.Time;
import java.util.List;

@Data
@Builder
public class RecyclingCenter {
    private Long id;
    private String name;
    private RcLocation location;
    private Time startingTime;
    private Time endTime;
    private List<Material> materials;
}