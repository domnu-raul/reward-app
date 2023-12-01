package com.rewardapp.backend.entities;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "recycling_centers")
public class RecyclingCenter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany(mappedBy = "recyclingCenter", cascade = CascadeType.ALL)
    private List<RecyclingCenterMaterials> materials = new ArrayList<>();
    @OneToOne(mappedBy = "recyclingCenter", cascade = CascadeType.ALL, optional = false)
    @PrimaryKeyJoinColumn
    private RecyclingCenterLocation location;
    @Column(name = "starting_time")
    private Time startingTime;
    @Column(name = "end_time")
    private Time endTime;


    public List<RecyclingCenterMaterials.MaterialType> getMaterials() {
        return materials
                .stream()
                .map(center -> center.getMaterial())
                .collect(Collectors.toList());
    }

    public void setMaterials(List<RecyclingCenterMaterials.MaterialType> materials) {
        this.materials = materials
                .stream()
                .map(materialType -> {
                    RecyclingCenterMaterials material = new RecyclingCenterMaterials();
                    material.setMaterial(materialType);
                    material.setRecyclingCenter(this);
                    return material;
                })
                .collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RecyclingCenterLocation getLocation() {
        return location;
    }

    public void setLocation(RecyclingCenterLocation recyclingCenterLocation) {
        this.location = recyclingCenterLocation;
        this.location.setRecyclingCenter(this);
    }

    public Time getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(Time startingTime) {
        this.startingTime = startingTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecyclingCenter that = (RecyclingCenter) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(materials, that.materials) && Objects.equals(location, that.location) && Objects.equals(startingTime, that.startingTime) && Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, materials, location, startingTime, endTime);
    }
}