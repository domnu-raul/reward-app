package com.rewardapp.backend.entities;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "recycling_centers")
public class RecyclingCenter {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "recycling_centers_id_seq")
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "recyclingCenter", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RecyclingCenterMaterial> recyclingCenterMaterials;
    @OneToOne(mappedBy = "recyclingCenter", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private RecyclingCenterLocation recyclingCenterLocation;
    @Column(name = "start_time")
    private Time startingTime;
    @Column(name = "end_time")
    private Time endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RecyclingCenterMaterial> getRecyclingCenterMaterials() {
        return recyclingCenterMaterials;
    }

    public void setRecyclingCenterMaterials(List<RecyclingCenterMaterial> recyclingCenterMaterials) {
        this.recyclingCenterMaterials = recyclingCenterMaterials;
    }

    public RecyclingCenterLocation getRecyclingCenterLocation() {
        return recyclingCenterLocation;
    }

    public void setRecyclingCenterLocation(RecyclingCenterLocation recyclingCenterLocation) {
        this.recyclingCenterLocation = recyclingCenterLocation;
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
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(recyclingCenterMaterials, that.recyclingCenterMaterials) && Objects.equals(recyclingCenterLocation, that.recyclingCenterLocation) && Objects.equals(startingTime, that.startingTime) && Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, recyclingCenterMaterials, recyclingCenterLocation, startingTime, endTime);
    }
}