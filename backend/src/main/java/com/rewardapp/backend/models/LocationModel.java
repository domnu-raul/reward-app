package com.rewardapp.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationModel {
    private String county;
    private String city;
    private String address;
    private String zipcode;
    private Double longitude;
    private Double latitude;
}
