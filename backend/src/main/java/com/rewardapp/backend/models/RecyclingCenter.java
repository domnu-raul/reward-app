package com.rewardapp.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.sql.Time;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecyclingCenter extends RepresentationModel<RecyclingCenter> {
    private Long id;
    private String name;
    private List<String> materials;
    private LocationModel location;
    private Time startTime;
    private Time endTime;
}
