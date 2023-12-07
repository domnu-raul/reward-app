package com.rewardapp.backend.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RcLocation {
    private Long id;
    private String county;
    private String city;
    private String address;
    private String zipcode;
    private Double longitude;
    private Double latitude;
}