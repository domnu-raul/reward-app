package com.rewardapp.backend.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "recycling_centers_locations")
public class RecyclingCenterLocation {
    @Id
    @Column(name = "recycling_center_id")
    private Long id;
    @Column(name = "county", columnDefinition = "VARCHAR(50) NOT NULL")
    private String county;
    @Column(name = "city", columnDefinition = "VARCHAR(50) NOT NULL")
    private String city;
    @Column(name = "address", columnDefinition = "VARCHAR(255) NOT NULL")
    private String address;
    @Column(name = "zipcode", columnDefinition = "VARCHAR(20) NOT NULL")
    private String zipcode;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "latitude")
    private Double latitude;

    @OneToOne
    @JoinColumn(name = "recycling_center_id")
    @MapsId
    private RecyclingCenter recyclingCenter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public RecyclingCenter getRecyclingCenter() {
        return recyclingCenter;
    }

    public void setRecyclingCenter(RecyclingCenter recyclingCenter) {
        this.recyclingCenter = recyclingCenter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecyclingCenterLocation that = (RecyclingCenterLocation) o;
        return Objects.equals(id, that.id) && Objects.equals(county, that.county) && Objects.equals(city, that.city) && Objects.equals(address, that.address) && Objects.equals(zipcode, that.zipcode) && Objects.equals(longitude, that.longitude) && Objects.equals(latitude, that.latitude) && Objects.equals(recyclingCenter, that.recyclingCenter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, county, city, address, zipcode, longitude, latitude, recyclingCenter);
    }
}